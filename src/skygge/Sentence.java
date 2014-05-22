/*
 * Copyright (C) 2014 json
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package skygge;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.minidev.json.*;

/**
 *
 * @author json
 */
public class Sentence {
    private int id;
    private String name;
    private String url;
    private List<Entry<String, String>> information;
    
    public Sentence(JSONObject sentenceData) {
        this.id = (Integer)sentenceData.get("id");
        this.name = (String)sentenceData.get("name");
        this.url = (String)sentenceData.get("url");
        
        this.information = new ArrayList<Entry<String, String>>();
        
        JSONArray sentenceInformation = (JSONArray)sentenceData.get("information");
        
        // TODO don't assume ids are continuous
        for (int i = 0; i < sentenceInformation.size(); i++) {
            JSONObject infoEntry = getInfoEntryById(sentenceInformation, i);
            
            if (infoEntry != null) {
                String title = (String)infoEntry.get("title");
                String data = (String)infoEntry.get("data");
                
                information.add(new SimpleEntry<String, String>(title, data));
            }
        }
        
    }
    
    private JSONObject getInfoEntryById(JSONArray sentenceInformation, int id) {
        for (int i = 0; i < sentenceInformation.size(); i++) {
            JSONObject infoEntry = (JSONObject)sentenceInformation.get(i);
            int entryId = (Integer)infoEntry.get("id");
            if (id == entryId) {
                return infoEntry;
            }
        }
        // TODO handle this case
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<Entry<String, String>> getInformation() {
        return information;
    }
    
    public String getInformationEntry(String title) {
        for (int i = 0; i < information.size(); i++) {
            Entry<String, String> e = information.get(i);
            if (e.getKey().equals(title)) {
                return e.getValue();
            }
        }
        // TODO handle this
        return null;
    }
    
    @Override public String toString()
    {
        // TODO make sentence ids start from 1
        return (getId() + 1) + ": " + getInformationEntry("Chinese (simp)");
    }
}
