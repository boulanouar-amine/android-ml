package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "projetdb";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_USER = "user";

        private static final String KEY_USERNAME = "username";
        private static final String KEY_PASSWORD = "password";


        private static final String TABLE_PERSONNE = "personne";
        private static final String KEY_ROWID = "ROWID";

        private static final String KEY_AGE = "age";
        private static final String KEY_GENRE= "genre";
        private static final String KEY_BLOODPRESSURE = "bloodPressure";
        private static final String KEY_CHOLESTEROL = "cholesterol";
        private static final String KEY_NA = "na";
        private static final String KEY_K = "k";
        private static final String KEY_DRUG = "drug";


        public DatabaseHandler(Context context) {
                super(context,DATABASE_NAME,null,DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

                String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                        + KEY_USERNAME + " TEXT NOT NULL PRIMARY KEY,"
                        + KEY_PASSWORD + " TEXT NOT NULL"
                        +")";

                String CREATE_PERSONNE_TABLE = "CREATE TABLE " + TABLE_PERSONNE + "("
                        + KEY_USERNAME + " TEXT,"
                        + KEY_AGE + " INTEGER NOT NULL,"
                        + KEY_GENRE + " TEXT NOT NULL,"
                        + KEY_BLOODPRESSURE + " TEXT NOT NULL,"
                        + KEY_CHOLESTEROL + " TEXT NOT NULL,"
                        + KEY_NA + " REAL NOT NULL,"
                        + KEY_K + " REAL NOT NULL,"
                        + KEY_DRUG + " TEXT,"
                        + "FOREIGN KEY(" + KEY_USERNAME + ") REFERENCES "+ TABLE_USER +" (" + KEY_USERNAME + ") ON DELETE CASCADE ON UPDATE CASCADE,"
                        + "PRIMARY KEY("+ KEY_AGE + ","
                                        + KEY_GENRE + ","
                                        + KEY_BLOODPRESSURE + ","
                                        + KEY_CHOLESTEROL + ","
                                        + KEY_NA + ","
                                        + KEY_K + ")"
                        + ")";

                db.execSQL(CREATE_USER_TABLE);
                db.execSQL(CREATE_PERSONNE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion , int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONNE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

                onCreate(db);
        }

        void addUser(User user){
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(KEY_USERNAME,user.getUsername());
                values.put(KEY_PASSWORD,user.getPassword());

                db.insert(TABLE_USER,null,values);
                db.close();

        }

        void addPersonne(Personne personne)  {

                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                if(personne.getUsername() != null)
                        values.put(KEY_USERNAME,personne.getUsername());
                values.put(KEY_AGE,personne.getAge());
                values.put(KEY_GENRE,personne.getGenre());
                values.put(KEY_BLOODPRESSURE,personne.getBloodPressure());
                values.put(KEY_CHOLESTEROL,personne.getCholesterol());
                values.put(KEY_NA,personne.getNa());
                values.put(KEY_K,personne.getK());
                values.put(KEY_DRUG,personne.getDrug());


                db.insert(TABLE_PERSONNE,null,values);

                db.close();


        }

        User getUser(String username){
                SQLiteDatabase db = this.getReadableDatabase();

                Cursor cursor = db.query(TABLE_USER,new String[] {KEY_USERNAME,KEY_PASSWORD},
                        KEY_USERNAME + "=?",
                        new String[] {String.valueOf(username)},null,null,null,null);

                if(cursor != null)
                        cursor.moveToFirst();

                assert cursor != null;

                User user = new User();

                user.setUsername(String.valueOf(cursor.getString(0)));
                user.setPassword(String.valueOf(cursor.getString(1)));

                cursor.close();

                return user;
        }

        Personne getPersonneFromRowId(String username){

                SQLiteDatabase db = this.getReadableDatabase();


                Cursor cursor = db.query(TABLE_PERSONNE,new String[] {
                                KEY_USERNAME,KEY_AGE,
                                KEY_GENRE,KEY_BLOODPRESSURE,
                                KEY_CHOLESTEROL,KEY_NA,KEY_K,KEY_DRUG},
                        KEY_ROWID + "=?",
                        new String[] {String.valueOf(username)},null,null,null,null);

                if(cursor != null)
                        cursor.moveToFirst();


                assert cursor != null;

                Personne personne = new Personne();

                personne.setUsername(String.valueOf(cursor.getString(0)));
                personne.setAge(Integer.parseInt(cursor.getString(1)));
                personne.setGenre(String.valueOf(cursor.getString(2)));
                personne.setBloodPressure(String.valueOf(cursor.getString(3)));
                personne.setCholesterol(String.valueOf(cursor.getString(4)));
                personne.setNa(Double.parseDouble(cursor.getString(5)));
                personne.setK(Double.parseDouble(cursor.getString(6)));
                personne.setDrug(String.valueOf(cursor.getString(7)));

                cursor.close();

                return personne;


        }

        public Personne getLastAddedPersonne(){
                SQLiteDatabase db = this.getReadableDatabase();

                Cursor cursor = db.query(TABLE_PERSONNE,new String[] {
                                KEY_ROWID,KEY_USERNAME,KEY_AGE,
                                KEY_GENRE,KEY_BLOODPRESSURE,
                                KEY_CHOLESTEROL,KEY_NA,KEY_K,KEY_DRUG},
                        null, null, null, null,
                         KEY_ROWID + " DESC", "1");

                if(cursor != null)
                        cursor.moveToLast();

                Personne personne = new Personne();

                assert cursor != null;
                personne.setId(Integer.parseInt(cursor.getString(0)));
                personne.setUsername(String.valueOf(cursor.getString(1)));
                personne.setAge(Integer.parseInt(cursor.getString(2)));
                personne.setGenre(String.valueOf(cursor.getString(3)));
                personne.setBloodPressure(String.valueOf(cursor.getString(4)));
                personne.setCholesterol(String.valueOf(cursor.getString(5)));
                personne.setNa(Double.parseDouble(cursor.getString(6)));
                personne.setK(Double.parseDouble(cursor.getString(7)));
                personne.setDrug(String.valueOf(cursor.getString(8)));

                cursor.close();

                return personne;

        }

        public List<User> getAllUsers(){
                List<User> userList = new ArrayList<>();

                String selectQuery = "SELECT * FROM " + TABLE_USER;

                SQLiteDatabase db = this.getWritableDatabase();

                Cursor cursor = db.rawQuery(selectQuery,null);

                if(cursor.moveToFirst()){
                        do {
                                User user = new User();

                                user.setUsername(cursor.getString(0));
                                user.setPassword(cursor.getString(1));

                                userList.add(user);

                        }while (cursor.moveToNext());
                }
                cursor.close();

                return userList;
        }

        public List<Personne> getAllPersonnes(){

                List<Personne> personneList = new ArrayList<>();

                String selectQuery = "SELECT " + KEY_ROWID + " , * FROM " + TABLE_PERSONNE;

                SQLiteDatabase db = this.getWritableDatabase();

                Cursor cursor = db.rawQuery(selectQuery,null);

                if(cursor.moveToFirst()){
                        do {
                                Personne personne = new Personne();
                                personne.setId(Integer.parseInt(cursor.getString(0)));
                                personne.setUsername(String.valueOf(cursor.getString(1)));
                                personne.setAge(Integer.parseInt(cursor.getString(2)));
                                personne.setGenre(String.valueOf(cursor.getString(3)));
                                personne.setBloodPressure(String.valueOf(cursor.getString(4)));
                                personne.setCholesterol(String.valueOf(cursor.getString(5)));
                                personne.setNa(Double.parseDouble(cursor.getString(6)));
                                personne.setK(Double.parseDouble(cursor.getString(7)));
                                personne.setDrug(String.valueOf(cursor.getString(8)));

                                personneList.add(personne);

                        }while (cursor.moveToNext());
                }
                cursor.close();

                return personneList;
        }

        public int updateUser(User user){
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();

                values.put(KEY_USERNAME,user.getUsername());
                values.put(KEY_PASSWORD,user.getPassword());

                return db.update(TABLE_USER,values,KEY_USERNAME + "=?",
                        new String[] {String.valueOf(user.getUsername())});
        }

        public void updatePersonne(Personne personne){
        SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();

                if(personne.getUsername() != null)
                        values.put(KEY_USERNAME,personne.getUsername());
                values.put(KEY_AGE,personne.getAge());
                values.put(KEY_GENRE,personne.getGenre());
                values.put(KEY_BLOODPRESSURE,personne.getBloodPressure());
                values.put(KEY_CHOLESTEROL,personne.getCholesterol());
                values.put(KEY_NA,personne.getNa());
                values.put(KEY_K,personne.getK());
                values.put(KEY_DRUG,personne.getDrug());

                db.update(TABLE_PERSONNE, values,
                        KEY_AGE + "=? and "
                                + KEY_GENRE + "=? and "
                                + KEY_BLOODPRESSURE + "=? and "
                                + KEY_CHOLESTEROL + "=? and "
                                + KEY_NA + "=? and "
                                + KEY_K + "=?",

                        new String[]{
                                Integer.toString(personne.getAge()),
                                String.valueOf(personne.getGenre()),
                                String.valueOf(personne.getBloodPressure()),
                                String.valueOf(personne.getCholesterol()),
                                Double.toString(personne.getNa()),
                                Double.toString(personne.getK()),
                        });

        }


        public void deleteUser(User user){
                SQLiteDatabase db = this.getWritableDatabase();

                db.delete(TABLE_USER,KEY_USERNAME + "=?",
                        new String[] {String.valueOf(user.getUsername())});

                db.close();

        }

        public void deletePersonne(Personne personne){
                SQLiteDatabase db = this.getWritableDatabase();

                db.delete(TABLE_PERSONNE,KEY_AGE + "=? and "
                                + KEY_GENRE + "=? and "
                                + KEY_BLOODPRESSURE + "=? and "
                                + KEY_CHOLESTEROL + "=? and "
                                + KEY_NA + "=? and "
                                + KEY_K + "=?",
                        new String[] {Integer.toString(personne.getAge()),
                                String.valueOf(personne.getGenre()),
                                String.valueOf(personne.getBloodPressure()),
                                String.valueOf(personne.getCholesterol()),
                                Double.toString(personne.getNa()),
                                Double.toString(personne.getK()),
                });

                db.close();

        }

        public int getUsersCount(){
                String countQuery = "SELECT * FROM " + TABLE_USER;
                SQLiteDatabase db = this.getWritableDatabase();
                Cursor cursor = db.rawQuery(countQuery,null);
                cursor.close();

                return cursor.getCount();

        }

        public int getPersonnesCount(){
                String countQuery = "SELECT * FROM " + TABLE_PERSONNE;
                SQLiteDatabase db = this.getWritableDatabase();
                Cursor cursor = db.rawQuery(countQuery,null);
                cursor.close();

                return cursor.getCount();

        }

        public Boolean checkUsernamePassword(User user){
                SQLiteDatabase db = this.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + KEY_USERNAME + " =? AND " + KEY_PASSWORD + " =? ",
                        new String[] {
                                String.valueOf(user.getUsername()),
                                String.valueOf(user.getPassword())}
                );
                if(cursor.getCount()>0){
                        cursor.close();
                        return true;
                }
                cursor.close();
                return false;
        }



}
