package com.lecturefeed.core.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WindowsSystem implements IOSSystem{
    @Override
    public Optional<InetAddress> getRoutingInterface() {
        try
        {
            Process pro = Runtime.getRuntime().exec("cmd.exe /c route print");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line;
            while((line = bufferedReader.readLine())!=null)
            {
                line = line.trim();
                List<String> tokens = Arrays.stream(line.split(" ")).filter(str -> str.length() > 0).toList();
                if(tokens.size() == 5 && tokens.get(0).equals("0.0.0.0"))
                {
                    return Optional.of(InetAddress.getByName(tokens.get(3)));
                }
            }
        }
        catch(IOException e) {}
        return Optional.empty();
    }
}
