package demo.service;

import demo.controller.dto.SaveUserDto;
import demo.model.Sector;
import demo.model.User;
import demo.repository.SectorRepository;
import demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(SaveUserDto dto){
        return null;

    }

    public Collection<User> findAll() {
        return null;
    }
}
