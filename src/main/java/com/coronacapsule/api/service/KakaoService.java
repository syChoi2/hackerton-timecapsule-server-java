package com.coronacapsule.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.coronacapsule.api.exception.BusinessException;
import com.coronacapsule.api.exception.ErrorCode;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class KakaoService {
	
	public String userIdFromKakao(String token) throws Exception {
		// 1. JWT 추출
		String access_Token = token;

		HashMap<String, Object> userInfo = new HashMap<>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		String socialId = "";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();
			if (responseCode != 200) {
				throw new BusinessException("Invalid Token", ErrorCode.TOKEN_ERROR);
			}
			System.out.println("responseCode : " + responseCode);
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

//			JsonParser parser = new JsonParser();
//			JsonElement element = parser.parse(result);

			JsonElement element = JsonParser.parseString(result);

			socialId = element.getAsJsonObject().get("id").getAsString();

			System.out.println(socialId);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 3. userId 추출
		// return claims.getBody().get("userId", Integer.class);
		return socialId;
	}
}
