import {Component, OnInit} from '@angular/core';
import {AdminSocket} from "./socket/adminSocket/admin.socket";
import {Question} from "./model/question/question.model";
import {AuthenticationService} from "./service/authenticationService/authentication.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'lecturefeed-web';

  constructor(
    private readonly authenticationService: AuthenticationService,
    private readonly adminSocket: AdminSocket)
  {}

  ngOnInit(): void {
    this.connect();
  }

  connect(){
    this.authenticationService.getAdminToken().subscribe(token => {
      this.adminSocket.connect(token.token).then(() => {
        this.adminSocket.onQuestion().subscribe(question => {
          console.log(question)
        });
        this.adminSocket.addQuestion(new Question("Test Question"));
      });
    })


  }


}
