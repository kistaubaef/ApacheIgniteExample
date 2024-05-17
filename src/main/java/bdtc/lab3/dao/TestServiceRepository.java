package bdtc.lab3.dao;

import bdtc.lab3.model.PersonEntity;
import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.Cache;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TestServiceRepository {
    private Ignite ignite;
    private CacheConfiguration<UUID, PersonEntity> personCacheConfiguration;

    /**
     * Constructs TestServiceRepository.
     * @param ignite                   Ignite
     * @param personCacheConfiguration Person cache configuration
     */
    public TestServiceRepository(
            final Ignite ignite,
            final CacheConfiguration<UUID, PersonEntity> personCacheConfiguration) {
        this.ignite = ignite;
        this.personCacheConfiguration = personCacheConfiguration;
    }

    /**
     * Saves person to ignite.
     * @param personEntity Person
     */
    public void save(final PersonEntity personEntity) {
        ignite.getOrCreateCache(personCacheConfiguration).put(personEntity.getId(), personEntity);
    }

    /**
     * Updates a person in the cache.
     * @param personEntity Person
     */
    public void update(final PersonEntity personEntity) {
        ignite.getOrCreateCache(personCacheConfiguration).put(personEntity.getId(), personEntity);
    }
    /**
     * Retrieves person from ignite by id.
     * @param id Person id
     * @return Person
     */
    public PersonEntity get(final UUID id) {
        return ignite.getOrCreateCache(personCacheConfiguration).get(id);
    }

    /**
     * Retrieves all persons from ignite.
     * @return List of persons
     */
    public List<PersonEntity> getAll() {
        Iterable<Cache.Entry<UUID, PersonEntity>> iterable = () -> ignite.getOrCreateCache(personCacheConfiguration)
                .iterator();

        List<PersonEntity> persons = StreamSupport
                .stream(iterable.spliterator(), false)
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());

        return persons;
    }

    /**
     * Deletes a person from the cache.
     * @param id Person id
     */
    public void delete(final UUID id) {
        ignite.getOrCreateCache(personCacheConfiguration).remove(id);
    }

}
