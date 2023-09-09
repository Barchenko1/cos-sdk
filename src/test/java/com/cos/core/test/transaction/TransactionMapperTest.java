package com.cos.core.test.transaction;

import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.factory.ConfigurationSessionFactory;
import com.cos.core.constant.DataSourcePoolType;
import com.cos.core.dto.ChildClass;
import com.cos.core.dto.ParentClass;
import com.cos.core.dto.TestClass;
import com.cos.core.dto.TestDto;
import com.cos.core.mapper.DtoEntityBind;
import com.cos.core.mapper.DtoEntityMapper;
import com.cos.core.mapper.IDtoEntityBind;
import com.cos.core.mapper.IDtoEntityMapper;
import com.cos.core.test.base.AbstractTransactionTest;
import com.cos.core.transaction.BasicTransactionManager;
import com.cos.core.transaction.ITransactionManager;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.cos.core.util.DataSourcePool.getDataSource;

@ExtendWith(DBUnitExtension.class)
public class TransactionMapperTest extends AbstractTransactionTest {
    private static ConnectionHolder connectionHolder;
    private static IDtoEntityBind dtoEntityBind;
    private static IDtoEntityMapper dtoEntityMapper;
    private static ITransactionManager transactionManager;

    @BeforeAll
    public static void getSessionFactory() {
        ConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
                ConnectionPoolType.HIKARI
        );
        sessionFactory = configurationSessionFactory.getSessionFactory();
        transactionManager = new BasicTransactionManager(sessionFactory);

        dataSource = getDataSource(DataSourcePoolType.HIKARI_DATASOURCE);
        connectionHolder = dataSource::getConnection;

        dtoEntityBind = new DtoEntityBind("bind");
        dtoEntityMapper = new DtoEntityMapper(dtoEntityBind);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void dtoMapperTest() {
        ParentClass parentClass = new ParentClass();
        parentClass.setName("parent");
        parentClass.setAge(80);

        ChildClass childClass = new ChildClass();
        childClass.setName("child");
        childClass.setAge(2);

        TestDto testDto = new TestDto();
        testDto.setName("test");
        testDto.setDtoAge(11);
        testDto.setParent(parentClass);
        testDto.getChildClassList().add(childClass);

        TestClass testClass = new TestClass();
        dtoEntityMapper.mapDtoToEntity(testDto, testClass, "testDtoBind");
        transactionManager.useTransaction(testClass);
    }
}
