package antd_access.services;

import antd_access.repository.db.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AntdUserDetailsService implements UserDetailsService {

    final UserRepository userRepository ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username) ;
//        if(user == null){
//            throw new UsernameNotFoundException("用户名不存在!");
//        }
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户名不存在!"));
    }
}
