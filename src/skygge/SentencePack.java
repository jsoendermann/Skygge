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

import java.util.ArrayList;
import java.util.List;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 *
 * @author json
 */
public class SentencePack {
    private int id;
    private String name;
    private List<Sentence> sentences;
    
    public SentencePack(JSONObject sentencePackData) {
        this.id = (Integer)sentencePackData.get("id");
        this.name = (String)sentencePackData.get("name");
        
        this.sentences = new ArrayList<Sentence>();
        
        JSONArray sentenceArray = (JSONArray)sentencePackData.get("sentences");
        
        for (int i = 0; i < sentenceArray.size(); i++) {
            this.sentences.add(new Sentence((JSONObject)sentenceArray.get(i)));
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }
    
    @Override public String toString()
    {
        return getName();
    }
}
