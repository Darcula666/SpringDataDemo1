package com.gfdz.springdata.test;

import com.gfdz.springdata.entity.Person;
import com.gfdz.springdata.repsotory.PersonRepsotory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2015/12/10.
 */
public class SpringDataTest {
    private ApplicationContext ctx = null;
    private PersonRepsotory personRepsotory;

    {
        ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        personRepsotory = ctx.getBean(PersonRepsotory.class);
    }

    @Test
    public void dataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());

    }

    @Test
    public void testJpa() {

    }

    @Test
    public void testHelloworldPerson() {

        Person person = personRepsotory.getByLastName("xiaobu");
        System.out.println(person);

    }
    @Test
    public void testKeyWord() {
        List<Person> persons=personRepsotory.getByLastNameEndingWithAndIdLessThan("b", 5);
        System.out.println(persons);


        System.out.println("###################################");
        persons=personRepsotory.getByEmailInOrBirthLessThan(Arrays.asList("bb@gfdz.com","cc@gfdz.com"),new Date());
        System.out.println(persons);

    }
    @Test
    public void testKeyWord2(){
        List<Person> persons=personRepsotory.getByAddressIdGreaterThan(5);
    }

    @Test
    public void testQueryAnntation(){
        Person persons=personRepsotory.getMaxIdPerson();
        System.out.println(persons);
    }
    @Test
    public void testQueryAnnotionParms1(){
        List<Person> persons=personRepsotory.testQueryAnnotionParms1("xiaobu","ab@gfdz.com");
        System.out.println(persons);
    }

    @Test
    public void testQueryAnnotionParms2(){
        List<Person> persons=personRepsotory.testQueryAnnotionParms2("aa@gfdz.com","xiaobu");
        System.out.println(persons);
    }

    @Test
    public void testQueryAnnotionLikeParms(){
        List<Person> persons=personRepsotory.testQueryAnnotionLikeParms("x", "a");
        System.out.println(persons);
    }
    @Test
    public void testQueryAnnotionLikeParms2(){
        List<Person> persons=personRepsotory.testQueryAnnotionLikeParms("x", "a");
        System.out.println(persons);
    }
     @Test
    public void testNativeQuery(){
         long count=personRepsotory.getTotalCount();
         System.out.println(count);

    }
}
