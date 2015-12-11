package com.gfdz.springdata.repsotory;


import com.gfdz.springdata.entity.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/10.
 * 1.Repository是一个空接口，即是一个标记接口
 * 2.若我们定义的接口继承了Repository，则该接口会被IOC容器识别为一个Repository Bean，纳入到IOC容器中，进而可以在该几口中定义满足一定规范的方法。
 * 3.实际上也可以通过@RepositoryDefinition()注解来替代继承Repository 接口(第一个是Bean，第二个是主键的类型)
 */

/**
 * 在Respository子接口中声明方法
 * 1.不是随便声明。而需要符合一定的规范；
 * 2.查询方法以find | read | get 开头
 * 3.涉及条件查询时，条件的属性用条件关键字连接。
 * 4.要注意的是：条件属性以首字母大写。
 * 5.支持属性的级联查询，若当前类有符合条件的属性，则优先使用，而不使用级联属性。
 * 若需要使用级联属性，则属性之间使用_进行连接
 */
//@RepositoryDefinition(domainClass =Person.class,idClass = Integer.class)
public interface PersonRepsotory extends Repository<Person, Integer> {
    //根据lastName 来获取对应的Person
    Person getByLastName(String lastName);

    //WHERE lastName Like ？% AND is<?
    List<Person> getByLastNameStartingWithAndIdLessThan(String lastName, Integer id);


    //WHERE lastName Like %? AND is<?
    List<Person> getByLastNameEndingWithAndIdLessThan(String lastName, Integer id);

    //WHERE email IN (?,?,?) OR birth <?
    List<Person> getByEmailInOrBirthLessThan(List<String> emails, Date birth);

    //WHERE a.id>?
    List<Person> getByAddressIdGreaterThan(Integer id);
    //查询id值最大的person
    //使用@Query注解可以自定义JPQL语句实现更灵活的查询
    @Query("select p from Person p where p.id=(select max(p2.id) from Person p2)")
    Person getMaxIdPerson();

    //为@Query注解传递参数的方式1：使用占位符
    @Query("select p from Person p where p.lastName=?1 and p.email=?2")
    List<Person> testQueryAnnotionParms1(String lastName,String email);



    //为@Query注解传递参数的方式2：使用命名参数
    @Query("select p from Person p where p.lastName=:lastName and p.email=:email")
    List<Person> testQueryAnnotionParms2(@Param("email")String email,@Param("lastName")String lastName);



    //SpringData 允许在占位符上添加%
    @Query("select p from Person p where p.lastName like %?1% and p.email like %?2%")
    List<Person> testQueryAnnotionLikeParms(String lastName,String email);

    //SpringData 允许在占位符上添加%
    @Query("select p from Person p where p.lastName like %:lastName% and p.email like %:email%")
    List<Person> testQueryAnnotionLikeParms2(@Param("lastName")String lastName,@Param("email")String email);

    //设置nativeQuery = true即可以使用原生的sql查询
    @Query(value = "SELECT COUNT(id) FROM jpa_persons" ,nativeQuery = true)
    long getTotalCount();

    //可以通过自定义的JPQL完成UPDATE和DELETE 注意JPQL不支持INSERT
    //在@Query注解中编写JPQL语句，但必须使用 @Modifying 进行修饰，以通知springData，这是应该
    //UPDATE或DELETE操作需要使用事务，此时需要定义Service层，在Service层的方法上添加事务操作。
    //默认情况下，springdata的每个方法上有事务，但都是只有一个只读事务，他们不能完成修改操作！
    @Modifying
    @Query("update Person p set p.email=:email where id=:id")
    void updatePeson(@Param("email")String email,@Param("id")Integer id);
}
