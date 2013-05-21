package org.jongo;

import com.foursquare.fongo.Fongo;
import org.junit.Assert;
import org.junit.Test;

public class FongoTest {

    static class MyBean {
        private String key;
        private int value;

        public MyBean() {
        }

        MyBean(String key, int value) {
            this.key = key;
            this.value = value;
        }

        String getKey() {
            return key;
        }

        void setKey(String key) {
            this.key = key;
        }

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "MyBean{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

    @Test
    public void fongoTest() throws Exception {
        Fongo fongo = new Fongo("Mocked Mongo server");
        Jongo jongo = new Jongo(fongo.getDB("test"));

        MongoCollection coll = jongo.getCollection("coll");

        coll.save(new MyBean("key", 0));

        coll.findAndModify("{key: 'key'}")
                .with("{$inc: {value: 1}}")
                .returnNew()
                .as(MyBean.class);

        Assert.assertTrue("count=" + coll.count(), coll.count() == 1);
    }
}
