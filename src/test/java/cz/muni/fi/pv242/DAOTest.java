package cz.muni.fi.pv242;

import static org.junit.Assert.assertEquals;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv242.persistence.BookDAO;
import cz.muni.fi.pv242.persistence.BorrowingDAO;
import cz.muni.fi.pv242.persistence.ReservationDAO;
import cz.muni.fi.pv242.persistence.UserDAO;
import cz.muni.fi.pv242.persistence.entity.Book;
import cz.muni.fi.pv242.persistence.entity.Reservation;
import cz.muni.fi.pv242.persistence.entity.User;
import cz.muni.fi.pv242.persistence.entity.UserRole;

/**
 * Created by honza on 5/17/16.
 */

@RunWith(Arquillian.class)
public class DAOTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "Library.war")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/classes/META-INF/persistence.xml"), "classes/META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(Book.class.getPackage())
                .addPackage(BookDAO.class.getPackage());
    }

    @Inject
    BookDAO bookDao;

    @Inject
    BorrowingDAO borrowingDao;

    @Inject
    ReservationDAO reservationDao;

    @Inject
    UserDAO userDao;

    private Book book;
    private User user;
    private Reservation reservation;


    @Before
    public void setUp()
    {
        book = new Book();
        book.setName("name");
        book.setTotalItems(2);
        book.setIsbn("afs");

        user = new User();
        user.setAge(50);
        user.setEmail("mail@gmail.xom");
        List<UserRole> l = new ArrayList<>();
        l.add(UserRole.CUSTOMER);
        //user.setRoles(l);
        user.setName("John");
        user.setSurname("Doe");
        user.setEnabled(true);


    }

    @Test
    public void bookTest()
    {
        bookDao.create(book);
        assertEquals(book,bookDao.getById(book.getId()));

        book.setTotalItems(10);
        bookDao.update(book);

        assertEquals(book,bookDao.getById(book.getId()));

        assertEquals(1,bookDao.getAll().size());

        bookDao.delete(book);

        assertEquals(0,bookDao.getAll().size());
    }

    @Test
    public void userTest()
    {
        userDao.create(user);
        assertEquals(user, userDao.getById(user.getId()));
        assertEquals(user, userDao.getByEmail(user.getEmail()));

        user.setSurname("new Name");
        userDao.update(user);
        assertEquals(user, userDao.getByEmail(user.getEmail()));


        assertEquals(1,userDao.getAll().size());

        userDao.delete(user);

        assertEquals(0,userDao.getAll().size());
    }

     // TODO @jbouska create reservation and Borrowing test

}
