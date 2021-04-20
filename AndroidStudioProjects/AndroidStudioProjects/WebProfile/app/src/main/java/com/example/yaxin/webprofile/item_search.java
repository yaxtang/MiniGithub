package com.example.yaxin.webprofile;

/**
 * Created by Yaxin on 11/6/2017.
 */

public class item_search {
    public String name;
    public String repo_url;
    public item_profile user;
    public boolean is_user;

    public item_search() {
        this.name = name;
        this.repo_url = repo_url;
        this.user = user;
    }
/*
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
            out.writeInt(mData);
    }
    public static final Parcelable.Creator<MyParcelable> CREATOR
        =new Parcelable.Creator<MyParcelable>()

    {
    public MyParcelable createFromParcel (Parcel in){
        return new MyParcelable(in);
    }

         public MyParcelable[] newArray ( int size){
             return new MyParcelable[size];
         }
     }

    ;
     
             

    private MyParcelable(Parcel in) {
         mData = in.readInt();
     }
}*/

}
