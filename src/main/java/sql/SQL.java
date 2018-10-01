package sql;
import java.util.List; 
import javax.persistence.EntityManager;
import javax.persistence.Query;
import orm.MealPlan;
import utils.Log;
import orm.Recipe;


public class SQL{
    
    private EntityManager em;

    public SQL(){
        try{
            em = PersistenceManager.INSTANCE.getEntityManager();
            
        }catch (Throwable ex) { 
            Log.edln("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex); 
        }       
    }
    
    public EntityManager getEM() {
        return em;
    }
    
    public void ClearCache() {
        try{
            em = PersistenceManager.INSTANCE.getEntityManager();
            
        }catch (Throwable ex) { 
            System.out.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex); 
        }   
    }
    
    public List<Recipe> GetAllRecipes(){
         Query q1 = this.em.createQuery("SELECT r FROM Recipe r ORDER BY r.prio");
         return q1.getResultList();
    }
    
    public List<MealPlan> GetAllMealPlans(){
         Query q1 = this.em.createQuery("SELECT m FROM MealPlan m");
         return q1.getResultList();
    }
    
    public List<Recipe> Query(String q){
         Query q1 = this.em.createQuery(q);
         return q1.getResultList();
    }
    
    /*
    public boolean InsertItem(Item item){
        try{
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
            return true;
        }catch (Exception e) {
            Log.eln("sql/: error inserting item: "+e);
            return false;
        }
    }

    public List<Store> SearchStores(String string, int user_id){
        try{
            em.getTransaction().begin();
            Query q;
            q = em.createQuery("SELECT s FROM Store s WHERE s.user_id=:user_id AND s.tags LIKE :string ");
            q.setParameter("user_id", user_id);
            q.setParameter("string", "%"+string+"%");
            em.getTransaction().commit();
            
            return q.getResultList();

        }catch (Exception e){
            Log.eln("Error creating search query: "+e);
            return null;
        }
    }
    
    public List<Item> SearchItems(String string, int store_id){
        try{
            em.getTransaction().begin();
            Query q;
            q = em.createQuery("SELECT i FROM Item i WHERE i.store_id=:store_id AND i.tags LIKE :string ");
            q.setParameter("store_id", store_id);
            q.setParameter("string", "%"+string+"%");
            em.getTransaction().commit();
            
            return q.getResultList();

        }catch (Exception e){
            Log.eln("Error creating search query: "+e);
            return null;
        }
    }

    
    public User Login(String email, String pw){
        Log.wln("sql/: Trying to request login data from DB");
        User user1;
        try{
            em.getTransaction().begin();
            Query q = em.createQuery("SELECT u FROM User u WHERE u.email=:email AND u.pw=:pw");
            q.setParameter("email", email);
            q.setParameter("pw", pw);
            List<User> users = q.getResultList();
            Log.wln("sql/: request users: result size="+users.size());
            
            if(users.size()!=1){
                return null;
            }else{
                user1 = (User) users.get(0);
            }
            em.getTransaction().commit();
            return user1;
        }catch (Exception e){
            Log.eln("sql/: login exception: "+e.getMessage());
            return null;
        }
    }
    
    
    
    public boolean InsertStore(Store store){
        try{
            em.getTransaction().begin();
            em.persist(store);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            Log.eln("sql/: error inserting store: "+e.getMessage());
            return false;
        }
    }
    
    public boolean InsertUser(User user){
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            Log.eln("sql/: error inserting user: "+e.getMessage());
            return false;
        }
    }
    
    public Item LoadItem(int id){
        try{
            em.getTransaction().begin();
            return em.getReference(Item.class, id);
        }catch (Exception e){
            Log.eln("sql/: error loading item: "+e.getMessage());
            return null;
        }
    }
    
    public boolean UpdateItem(Item item){
        
        try{
            em.merge(item);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            Log.eln("sql/: error updating item: "+e.getMessage());
            return false;
        }
    }
    /*
    
    
    public List<Store> GetAllStores(int user_id){
        Session session = this.factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Store WHERE user_id=:user_id");
            q.setParameter("user_id", user_id);
            
            List<Store> stores = q.list();            
            
            tx.commit();
            return stores;
        }catch (HibernateException e){
            if (tx!=null) tx.rollback();
        }finally {
            session.close(); 
        }
        return null;
    }
    
    public Item GetItem(int item_id){
        Session session = this.factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Item WHERE id=:item_id");
            q.setParameter("item_id", item_id);
            
            List<Item> items = q.list();            
            
            tx.commit();
            return items.get(0);
        }catch (HibernateException e){
            if (tx!=null) tx.rollback();
        }finally {
            session.close(); 
        }
        return null;
    }
    
    public Store GetStore(int store_id){
        Session session = this.factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Store WHERE id=:store_id");
            q.setParameter("store_id", store_id);
            
            List<Store> stores = q.list();            
            
            tx.commit();
            return stores.get(0);
        }catch (HibernateException e){
            if (tx!=null) tx.rollback();
        }finally {
            session.close();
        }
        return null;
    }
    
    public User GetUser(int user_id){
        Session session = this.factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM User WHERE user_id=:user_id");
            q.setParameter("user_id", user_id);
            
            List<User> users = q.list();            
            
            tx.commit();
            return users.get(0);
        }catch (HibernateException e){
            if (tx!=null) tx.rollback();
        }finally {
            session.close();
        }
        return null;
    }
    
    public boolean DeleteItem(int item_id){
        Session session = this.factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query q = session.createQuery("DELETE FROM Item WHERE item_id=:item_id");
            q.setParameter("item_id", item_id);
        }catch (HibernateException e){
            if (tx!=null) tx.rollback();
            return false;
        }finally {
            session.close();
        }
        return true;
    }
    
    public boolean DeleteStore(int store_id){
        Session session = this.factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query q = session.createQuery("DELETE FROM Store WHERE store_id=:store_id");
            q.setParameter("store_id", store_id);
        }catch (HibernateException e){
            if (tx!=null) tx.rollback();
            return false;
        }finally {
            session.close();
        }
        return true;
    }
    
    public boolean DeleteUser(int user_id){
        Session session = this.factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query q = session.createQuery("DELETE FROM User WHERE user_id=:user_id");
            q.setParameter("user_id", user_id);
        }catch (HibernateException e){
            if (tx!=null) tx.rollback();
            return false;
        }finally {
            session.close();
        }
        return true;
    }
    
    public boolean CheckUserPermission(int user_id, int store_id){
        Store store = this.GetStore(store_id);
        Log.wln("sql/: Ask for permission for user_id="+user_id+" for store_id="+store_id+"; store is permitted for user with id="+store.getUser_id());
        return store.getUser_id() == user_id;
    }
    
    public boolean CheckUserPermission(int user_id, Store store){
        Log.wln("sql/: Ask for permission for user_id="+user_id+" for store_id="+store.getId()+"; store is permitted for user with id="+store.getUser_id());
        return store.getUser_id() == user_id;
    }
*/

    
}
