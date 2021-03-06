package xyz.manzodev.lasttry;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import xyz.manzodev.lasttry.Model.Address;
import xyz.manzodev.lasttry.Model.Model;
import xyz.manzodev.lasttry.Model.Relation;
import xyz.manzodev.lasttry.Utils.Relationship;

public class DatabaseHandle extends SQLiteOpenHelper {
    private static String DB_NAME = "People.db";
    Context context;

    public DatabaseHandle(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q1 = "create table people(\n" +
                "id integer primary key,\n" +
                "name text,dispRela text\n" +
                ");";
        String q2="create table relative(\n" +
                "main_id integer ,\n" +
                "id integer ,\n" +
                "name text,\n" +
                "rela integer,\n" +
                "foreign key (main_id) references people(id)\n" +
                ");";

        String q3="create table address(main_id integer,latitude double,longitude double,mAddress text,foreign key (main_id) references people(id));";

        String q4="create table detail_info(main_id integer,phone text,dBirth integer,dDeath integer,age integer" +
                ",work text,email text,description text" +
                ",foreign key (main_id) references people(id));";



        db.execSQL(q1);
        db.execSQL(q2);
        db.execSQL(q3);
        db.execSQL(q4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            db.execSQL("drop table if exists people ");
            db.execSQL("drop table if exists relative ");
            onCreate(db);
        }
    }


    //todo table people
    public void addPerson(Model model){
        SQLiteDatabase db = getWritableDatabase();
        String q = "insert into people values("+model.getId()+",'"+model.getName()+"','"+model.getDispRela()+"');";
        db.execSQL(q);
    }

    public void removePerson(int id){
        SQLiteDatabase db = getWritableDatabase();
        String q = "delete from people where (id="+id+");";
        String q1 = "delete from relative where (main_id = "+id+" or id="+id+");";
        String q2 = "delete from address where (main_id = "+id+" or id="+id+");";
        String q3 = "delete from detail_info where (main_id = "+id+" or id="+id+");";
        db.execSQL(q);
        db.execSQL(q1);
        db.execSQL(q2);
        db.execSQL(q3);
    }

    public ArrayList<Model> getAllPerson(){
        ArrayList<Model> models = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "select * from people";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String dispRela = c.getString(c.getColumnIndex("dispRela"));
            Model model = new Model(id,name,dispRela);
            models.add(model);
        }
        return models;
    }

    //todo relative table

    public void addRelation(Model m1,Model m2,int relation){
        //todo if rela = 4 , add wife
        //rela = 2,0 , add son's mother
        SQLiteDatabase db = getWritableDatabase();
        String q = "insert into relative values ("+m1.getId()+","+ m2.getId()+",'"+m2.getName()+"',"+relation+")";
        String q2 = "insert into relative values ("+m2.getId()+","+ m1.getId()+",'"+m1.getName()+"',"+(2-relation)+")";
        db.execSQL(q);
        db.execSQL(q2);
    }

    public void removeRelation(int id1,int id2){
        SQLiteDatabase db = getWritableDatabase();
        String q = "delete from relative where (main_id = "+id1+" and id="+id2+");";
        String q1 = "delete from relative where (main_id = "+id2+" and id="+id1+");";
        db.execSQL(q);
        db.execSQL(q1);
    }

    public ArrayList<Model> getRelation(int id, String s){
        int rela = Relationship.convertRelationshipInt(s);
        ArrayList<Model> models = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "select * from relative where (main_id = "+id+" and rela = "+rela+");";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            int mId = c.getInt(c.getColumnIndex("id"));
            String mName = c.getString(c.getColumnIndex("name"));
            models.add(new Model(mId,mName));
        }
        if (models.size()>0){
            return models;
        }
        else return null;
    }


    public ArrayList<Relation> getAllRelation(int id){
        ArrayList<Relation> modelRelas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "select * from relative where main_id = "+id+";";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            int mId = c.getInt(c.getColumnIndex("id"));
            String mName = c.getString(c.getColumnIndex("name"));
            int rela = c.getInt(c.getColumnIndex("rela"));
            modelRelas.add(new Relation(Relationship.convertRelationshipString(rela),new Model(mId,mName)));
        }
        if (modelRelas.size()>0){
            return modelRelas;
        }
        else return null;
    }

    //todo address table


}
