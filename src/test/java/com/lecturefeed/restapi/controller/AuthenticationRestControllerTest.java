package com.lecturefeed.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.inject.matcher.Matchers;
import com.lecturefeed.authentication.InetAddressSecurityService;
import com.lecturefeed.authentication.jwt.TokenService;
import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.manager.ParticipantManager;
import com.lecturefeed.manager.SessionManager;
import com.lecturefeed.model.ParticipantAuthRequestModel;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.repository.service.ParticipantDBService;
import com.lecturefeed.repository.service.SessionDBService;
import com.lecturefeed.socket.controller.service.SessionDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthenticationRestControllerTest {

    @Autowired//needed for JSON conversion
    private ObjectMapper objectMapper;

    MockMvc mockMvc;

    @Mock//needed for SessionManager and ParticipantManager
    SessionDataService sessionDataService;

    @Mock//needed for SessionManager
    SessionDBService sessionDBService;

    @Mock//needed for ParticipantManager
    ParticipantDBService participantDBService;

    @Autowired
    TokenService tokenService;
    @Autowired
    InetAddressSecurityService inetAddressSecurityService;

    ParticipantManager participantManager;

    SessionManager sessionManager;

    AuthenticationRestController authenticationRestController;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManager(sessionDataService,sessionDBService);
        participantManager = new ParticipantManager(sessionManager,sessionDataService,participantDBService);
        authenticationRestController = new AuthenticationRestController(sessionManager,participantManager,tokenService,inetAddressSecurityService);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationRestController).build();
    }


    //checks the creation of a new User Token
    @Test
    void participantAuth() throws Exception {
        int sessionId = 1;
        String username = "Rainer Zufall";
        String sessionCode = "super-secret";
        int userId = 1;

        Session session = Session.builder().sessionCode("super-secret").name("").id(sessionId).build();

        ParticipantAuthRequestModel requestModel = new ParticipantAuthRequestModel(sessionId,username,sessionCode);
        String jsonRequestModel = objectMapper.writeValueAsString(requestModel);

        when(sessionDBService.findById(sessionId)).thenReturn(session);

        //the id's are usually set by teh db
        when(participantDBService.save(any())).thenAnswer(invocation -> {
            Participant participant = invocation.getArgument(0);
            participant.setId(userId);
            return participant;
        });


        String resultString = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/participant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestModel))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //parse the returned JSON-TokenModel into a TokenModel
        TokenModel returnedToken = objectMapper.readValue(resultString,TokenModel.class);

        //check that the participant was added to the session
        assertEquals(1,session.getParticipants().size());

        //check if the token matches
        assertEquals(sessionId,tokenService.getTokenValue("sessionId",returnedToken).asInt());
        assertEquals(username,tokenService.getTokenValue("username",returnedToken).asString());
        assertEquals(UserRole.PARTICIPANT.getRole(),tokenService.getTokenValue("role",returnedToken).asString());
        assertEquals(userId,tokenService.getTokenValue("id",returnedToken).asInt());
    }
}
