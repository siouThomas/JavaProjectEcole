package com.app.politcus.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.app.politcus.App;


/**
 * Created by Thomas on 19/10/2016.
 */
public class QuestionContentProvider extends ContentProvider{

    public static final String APP_NAME = "com.app.politcus";
    //:::::::::::::::::::::::::://
    //:: URI d'exposition
    //:::::::::::::::::::::::::://
    public static final Uri CONTENT_URI = Uri.parse("content://"+APP_NAME);


    // Constantes pour identifier les requetes
    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;
    private static final int SINGLE_ROW_BEST_SCORE = 3;
    private static final int ALLROWS_BEST_SCORE = 4;

    // Uri matcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(APP_NAME,"elements",ALLROWS);
        uriMatcher.addURI(APP_NAME,"elements/#",SINGLE_ROW);
        uriMatcher.addURI(APP_NAME,"bestscores/#",SINGLE_ROW_BEST_SCORE);
        uriMatcher.addURI(APP_NAME,"bestscores",ALLROWS_BEST_SCORE);
    }


    private MySQLiteHelper myQuestionLiteHelper;



    @Override
    public boolean onCreate() {
        // construction/ouverture de la base de donnée
        myQuestionLiteHelper = new MySQLiteHelper(
                App.getContext());

        return true;
    }

    private String whichTable(int rowType)
    {
        if(rowType == ALLROWS || rowType == SINGLE_ROW)
            return MySQLiteHelper.TABLE_QUIZZ;
        if(rowType == SINGLE_ROW_BEST_SCORE || rowType == ALLROWS_BEST_SCORE)
            return MySQLiteHelper.TABLE_BEST_SCORE_QUIZZ;

        throw new IllegalArgumentException("URI rowtype not allow");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //ouverture de la base de donnée
        SQLiteDatabase db;
        try{
            db = myQuestionLiteHelper.getWritableDatabase();
        }catch(SQLiteException e)
        {
            System.out.println(e.getMessage());
            throw e;
        }

        //parametres de la requete SQL sans groupBy et sans having
        String groupBy = null;
        String having = null;

        //construction de la requete
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String rowId;
        //Ajout des parametres contenu dans l'uri à la requete
        switch (uriMatcher.match(uri)){
            case SINGLE_ROW :
                rowId = uri.getPathSegments().get(1);
                //ajout de la clause where, si on demande un element précis
                queryBuilder.appendWhere(MySQLiteHelper.COLUMN_ID + "=" + rowId);
                break;

            case SINGLE_ROW_BEST_SCORE :
                rowId = uri.getPathSegments().get(1);
                //ajout de la clause where, si on demande un element précis
                queryBuilder.appendWhere(MySQLiteHelper.COLUMN_ID + "=" + rowId);
                break;

            case ALLROWS :
                // on prend tous les enregistrements
                break;

            case ALLROWS_BEST_SCORE :
                // on prend tous les enregistrements
                break;
            default: break;
        }

        //ajout de la table
        try{
            queryBuilder.setTables(whichTable(uriMatcher.match(uri)));
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }

        //execution de la requete
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case ALLROWS : return "vnd.android.cursor.dir/vnd.paad.elemental";
            case SINGLE_ROW : return "vnd.android.cursor.item/vnd.paad.elemental";
            case SINGLE_ROW_BEST_SCORE : return "vnd.android.cursor.item/vnd.paad.elemental";
            case ALLROWS_BEST_SCORE : return "vnd.android.cursor.dir/vnd.paad.elemental";
            default: throw new IllegalArgumentException("URI non reconnue");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        //hack column vide
        String nullColumnHack = null;

        //ouverture de la base de donnée
        long id;
        SQLiteDatabase db;
        try{
            db = myQuestionLiteHelper.getWritableDatabase();
            //Insere les valeurs (seulement dans la table de question du quizz)
            id = db.insertOrThrow(MySQLiteHelper.TABLE_QUIZZ, nullColumnHack, contentValues);
        }catch( SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }

        //on verifie quand même l'id même si le trow devrait suffir
        if (id > -1){
            // contruit l'uri de la ligne crée
            Uri instertedId = ContentUris.withAppendedId(CONTENT_URI, id);

            //notifie le changement des données
            App.getContext().getContentResolver().notifyChange(instertedId, null);

            return instertedId;
        }

        return null;
    }
    private String rowSelection(String rowId, String selection)
    {
        return MySQLiteHelper.COLUMN_ID + "=" + rowId
                + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
    }

    private String selectionForUpdateOrDelete(Uri uri, String selection)
    {

        //Ajout des parametres contenu dans l'uri à la requete
        switch (uriMatcher.match(uri)){
            case SINGLE_ROW :
                selection = rowSelection(uri.getPathSegments().get(1),selection);
                break;

            case SINGLE_ROW_BEST_SCORE :
                // on ne supprime pas les score
                return "0";

            case ALLROWS :
                // on ne supprime pas en masse
                return "0";

            case ALLROWS_BEST_SCORE :
                // on ne supprime pas en masse
                return "0";

            default: return "0";
        }

        if (selection == null) {
            throw new IllegalArgumentException("Error selection");
        }

        return selection;
    }


    @Override //seulement dans table quizz
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        //ouverture de la base de donnée
        SQLiteDatabase db;
        try{
             db = myQuestionLiteHelper.getWritableDatabase();
            //on fait la selection
            selection = selectionForUpdateOrDelete(uri,selection);
        }catch(SQLiteException | IllegalArgumentException  e)
        {
            System.out.println(e.getMessage());
            return 0;
        }


        // on effectue la suppression
        int deleteCount = db.delete(MySQLiteHelper.TABLE_QUIZZ, selection, selectionArgs);

        //notifie le changement des données
        App.getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    @Override //seulement dans table quizz
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        SQLiteDatabase db;
        try{
            //ouverture de la base de donnée
            db = myQuestionLiteHelper.getWritableDatabase();
            //on fait la selection
            selection = selectionForUpdateOrDelete(uri,selection);
        }catch(SQLiteException | IllegalArgumentException  e)
        {
            System.out.println(e.getMessage());
            return 0;
        }

        // on effectue l'update
        int updateCount = db.update(MySQLiteHelper.TABLE_QUIZZ, contentValues, selection, selectionArgs);

        //notifie le changement des données
        App.getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }

}
