package com.birthday.video.maker.Birthday_Remainders;

public class User {
    private long _birthday;
    private int _id;
    private String _image;
    private String _name;

    public User() {
    }

    public User(int id, String name, long birthday, String image) {
        this._id = id;
        this._name = name;
        this._birthday = birthday;
        this._image = image;
    }

    public User(String name, long birthday, String image) {
        this._name = name;
        this._birthday = birthday;
        this._image = image;
    }

    public int get_id() {
        return this._id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return this._name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public long get_birthday() {
        return this._birthday;
    }

    public void set_birthday(long _birthday) {
        this._birthday = _birthday;
    }

    public String get_image() {
        return this._image;
    }

    public void set_image(String _image) {
        this._image = _image;
    }
}
