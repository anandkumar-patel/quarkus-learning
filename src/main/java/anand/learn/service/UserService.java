package anand.learn.service;

import anand.learn.entity.User;
import anand.learn.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.listAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findByIdOptional(id);
    }

    @Transactional
    public User create(User user) {
        userRepository.persist(user);
        return user;
    }

    @Transactional
    public Optional<User> update(Long id, User updatedUser) {
        return userRepository.findByIdOptional(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setAge(updatedUser.getAge());
                    user.setEmail(updatedUser.getEmail());
                    return user;
                });
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.deleteById(id);
    }
}
