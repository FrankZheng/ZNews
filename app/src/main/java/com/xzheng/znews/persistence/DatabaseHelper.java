package com.xzheng.znews.persistence;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xzheng.znews.R;

@Singleton
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "com.xzheng.app.xnewsreader.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;
	
	@Inject
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, 
			int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public <T, ID> Dao<T, ID> createDao(Class<T> clazz, Class<ID> id) throws SQLException {
		ConnectionSource source = getConnectionSource();
		TableUtils.createTableIfNotExists(source, clazz);
		Dao<T, ID> dao = DaoManager.createDao(source, clazz);
		// Setup the Object cache
		//dao.setObjectCache(_modelObjectCache);
		return dao;
	}

}
