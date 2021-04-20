package com.example.yaxin.webprofile;

/**
 * Created by Yaxin on 10/24/2017.
 */

public class item_profile {
    public String name;
    public String github;
    public String bio;
    public String email;
    public String avatar;
    public int follower_count;
    public int follwering_count;
    public int publicRepos_count;
    
    public item_profile(
            String name,
            String github,
            String bio,
            String avatar,
            String email,
            int follower_count,
            int follwering_count,
            int publicRepos_count){
       this.name = name;
       this. github = github;
        this.avatar = avatar;
       this.bio = bio;
       this.email = email;
       this.follower_count = follower_count;
        this.follwering_count = follwering_count;
        this.publicRepos_count = publicRepos_count;

    }

}
