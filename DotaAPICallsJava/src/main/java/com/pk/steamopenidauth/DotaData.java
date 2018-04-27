/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.steamopenidauth;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author PraveenKumar
 */
public class DotaData {

    public void getData() {
        try {
            // match_id
            //steam_key
            HttpResponse<JsonNode> response = Unirest.get("https://community-dota-2.p.mashape.com/IDOTA2Match_570/GetMatchDetails/V001/?key=5A8CEA48F10A17DA97EA4332029B392F&match_id=342520389&matches_requested=25")
                    .header("X-Mashape-Key", "GvffdTRUbRmshun61uIPL61ia1hmp1yQ1WLjsnQWIn0B6yI2AM")
                    .header("Accept", "application/json")
                    .asJson();
            System.out.println(response.getBody().getObject().getJSONObject("result").get("barracks_status_dire"));
        } catch (UnirestException e) {
            System.out.println("Exception");
        }
    }
    public void getCount(){
        try {
            // match_id
            //steam_key
            HttpResponse<JsonNode> response = Unirest.get("https://api.opendota.com/api/players/109548492/matches?limit=1")
                    .asJson();
             JSONArray f = response.getBody().getArray();
                 JSONObject h = f.getJSONObject(0);
                 
                 System.out.println(h.getInt("kills"));
                 System.out.println(h.getLong("match_id"));
                 System.out.println(h.getInt("duration"));
                 
             
             ;
        } catch (UnirestException e) {
            System.out.println("Exception");
        }
    }
    public void getSteam64ID(String userName,String key){
        try {
            HttpResponse<JsonNode> response = Unirest.get("http://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001/?key="+key+"&vanityurl="+userName)
                    .asJson();
            JSONObject p = (JSONObject)response.getBody().getObject().get("response");
            System.out.println(p.get("steamid"));
            System.out.println(Long.toString(Long.parseLong(p.get("steamid").toString().substring(3))- 61197960265728L));
        } catch (UnirestException e) {
            System.out.println("Exception");
        }
        
    }
    public void getData(String playerID){
        URL url = null;
        try {
            url = new URL("https://api.opendota.com/api/players/"+playerID+"/matches?limit=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            response.append("{ matchresult:");
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            response.append("}");
            in.close();
            JSONObject myResponse = new JSONObject(response.toString());
            
            System.out.println(myResponse.get("matchresult"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public String getSteam64(String userName,String key){
        URL url = null;
        try {
            url = new URL("http://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001/?key="+key+"&vanityurl="+userName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer responseAsString = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                responseAsString.append(inputLine);
            }
            in.close();
            JSONObject responseAsJSON = new JSONObject(responseAsString.toString());
            JSONObject v = (JSONObject)responseAsJSON.get("response");
            System.out.println(v.get("steamid"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public JSONObject getJ(String userName, String key){
        OkHttpClient client = new OkHttpClient();
        String id32 = null;

        Request request = new Request.Builder().url("http://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001/?key="+key+"&vanityurl="+userName).build();
        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();
            System.out.println(data);
            JSONObject j = new JSONObject(data);
            JSONObject jj = (JSONObject)j.get("response");
            String id64 = (String) jj.getString("steamid");
            id32 = Long.toString(Long.parseLong(id64.substring(3))- 61197960265728L);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request requestForMatches = new Request.Builder().url("https://api.opendota.com/api/players/"+id32+"/matches?limit=1").build();
        try {
            Response response = client.newCall(requestForMatches).execute();
            String matchdata = response.body().string();
            System.out.println(matchdata);
            JSONArray p = new JSONArray(matchdata);
            System.out.println(p);
            Gson g = new Gson();
            JsonElement element = g.fromJson(matchdata, JsonElement.class);
            JsonArray h = element.getAsJsonArray();
            
            System.out.println(h);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        //new DotaData().getIndex();
        DotaData d = new DotaData();
        d.getJ("silversky753", "5A8CEA48F10A17DA97EA4332029B392F");
        //new DotaData().getSteam64ID("silversky753", "5A8CEA48F10A17DA97EA4332029B392F");
    }

}
