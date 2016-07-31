package app.injector;

import app.dao.InterPersonDao;
import app.dao.json.JsonPersonDao;
import app.dao.mysql.MySqlPersonDao;
import app.dao.sqlite.SQlitePersonDao;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;

/**
 * Created by Antic on 27.07.2016.
 */
public class InjectorConfiguration extends AbstractModule
{
  protected void configure() {
//    bind(InterPersonDao.class).to(InMemoryDB.class);
//    bind(InterPersonDao.class).to(MySqlPersonDao.class);
//    bind(InterPersonDao.class).to(JsonPersonDao.class);
    bind(InterPersonDao.class).to(SQlitePersonDao.class);
    bind(EventBus.class).asEagerSingleton();
  }
}
