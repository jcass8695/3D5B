/**
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.firebase.udacity.friendlychat;

import java.util.ArrayList;

public class FriendlyMessage {

    private String text;
    private String name;
    private String fbaseKey;
    private String location;
    private int upvote;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String text, String name, String location) {
        this.text = text;
        this.name = name;
        this.location = location;
        this.upvote = 0;
        this.fbaseKey = "";

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getFbaseKey() { return fbaseKey; }

    public void setFbaseKey(String key) { this.fbaseKey = key; }

    public String getLocation() { return location;}

    public void setName(String name) {
        this.name = name;
    }

    public int getUpvote() { return upvote; }

    public void setUpvote(int upvote) { this.upvote = upvote; }

    public void incrementUpvote() { this.upvote++; }

}
