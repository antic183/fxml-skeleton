package app.dao.util;

/**
 * Created by Antic on 30.07.2016.
 */
public class IdGenerator
{
  private static long id = 0;

  public static long getNext() {
    return ++id;
  }

  void setId(long id){
    this.id = id;
  }
}
