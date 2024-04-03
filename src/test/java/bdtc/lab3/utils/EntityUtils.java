package bdtc.lab3.utils;

import bdtc.lab3.dao.TestServiceRepository;
import bdtc.lab3.model.PersonEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ignite.Ignite;

public class EntityUtils {
    private TestServiceRepository testServiceRepository;
    private Ignite ignite;

    public EntityUtils(TestServiceRepository testServiceRepository, Ignite ignite) {
        this.testServiceRepository = testServiceRepository;
        this.ignite = ignite;
    }



    public PersonEntity createAndSavePersonEntity() {
        PersonEntity personEntity = createPersonEntity();
        testServiceRepository.save(personEntity);

        return personEntity;
    }

    public void clearPersonEntitiesCache() {
        ignite.getOrCreateCache("person").clear();
    }

    public static PersonEntity createPersonEntity() {
        return new PersonEntity(rdmStr(15));
    }

    public static String rdmStr(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
}
