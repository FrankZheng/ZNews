package com.xzheng.znews.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;
import javax.inject.Singleton;

import android.content.Context;
import android.content.res.AssetManager;

@Singleton
public class AssetUtil {
	
	@Inject
	public AssetUtil() {
		
	}
	
	public String ReadFromfile(String fileName, Context context) {
	    StringBuilder returnString = new StringBuilder();
	    InputStream fIn = null;
	    InputStreamReader isr = null;
	    BufferedReader input = null;
	    try {
	        fIn = context.getResources().getAssets()
	                .open(fileName, AssetManager.ACCESS_STREAMING);
	        isr = new InputStreamReader(fIn);
	        input = new BufferedReader(isr);
	        String line = "";
	        while ((line = input.readLine()) != null) {
	            returnString.append(line);
	        }
	    } catch (Exception e) {
	        e.getMessage();
	    } finally {
	        try {
	            if (isr != null)
	                isr.close();
	            if (fIn != null)
	                fIn.close();
	            if (input != null)
	                input.close();
	        } catch (Exception e2) {
	            e2.getMessage();
	        }
	    }
	    return returnString.toString();
	}
}
