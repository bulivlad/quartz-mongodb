package com.novemberain.quartz.mongodb;

import com.mongodb.client.MongoClient;
import com.novemberain.quartz.mongodb.clojure.DynamicClassLoadHelper;
import org.quartz.spi.ClassLoadHelper;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author vbulimac on 15/08/2020.
 */
public class SpELMongoDBJobStore extends MongoDBJobStore {
    public SpELMongoDBJobStore() {
        super();
    }

    public SpELMongoDBJobStore(MongoClient mongo) {
        super(mongo);
    }

    public SpELMongoDBJobStore(String mongoUri, String username, String password) {
        super(mongoUri, username, password);
    }

    @Override
    protected ClassLoadHelper getClassLoaderHelper(ClassLoadHelper original) {
        return new DynamicClassLoadHelper();
    }

    @Override
    public void setAddresses(String addresses) {
        ExpressionParser parser = new SpelExpressionParser();
        this.addresses = Arrays.stream(addresses.split(",")).map(e -> {
            Expression exp = parser.parseExpression(addresses);
            return (String) exp.getValue();
        }).toArray(String[]::new);
    }
}
