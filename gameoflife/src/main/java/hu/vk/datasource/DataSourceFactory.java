package hu.vk.datasource;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Factory to create a new datasource
 */
public class DataSourceFactory {

    public static DataSource factory(String type) {
        switch(type) {
            case "LIF":
                return new LIFSource();
            case "MySQL":
                throw new NotImplementedException();
        }

        return null;
    }
}
