package com.kun.keep.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.kun.keep.keep.bean.KeepData;
import com.kun.keep.keep.bean.DBdKeepData;

import com.kun.keep.greendao.KeepDataDao;
import com.kun.keep.greendao.DBdKeepDataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig keepDataDaoConfig;
    private final DaoConfig dBdKeepDataDaoConfig;

    private final KeepDataDao keepDataDao;
    private final DBdKeepDataDao dBdKeepDataDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        keepDataDaoConfig = daoConfigMap.get(KeepDataDao.class).clone();
        keepDataDaoConfig.initIdentityScope(type);

        dBdKeepDataDaoConfig = daoConfigMap.get(DBdKeepDataDao.class).clone();
        dBdKeepDataDaoConfig.initIdentityScope(type);

        keepDataDao = new KeepDataDao(keepDataDaoConfig, this);
        dBdKeepDataDao = new DBdKeepDataDao(dBdKeepDataDaoConfig, this);

        registerDao(KeepData.class, keepDataDao);
        registerDao(DBdKeepData.class, dBdKeepDataDao);
    }
    
    public void clear() {
        keepDataDaoConfig.clearIdentityScope();
        dBdKeepDataDaoConfig.clearIdentityScope();
    }

    public KeepDataDao getKeepDataDao() {
        return keepDataDao;
    }

    public DBdKeepDataDao getDBdKeepDataDao() {
        return dBdKeepDataDao;
    }

}