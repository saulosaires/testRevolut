package test.revolut.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import test.revolut.repository.impl.AccountRepository;
import test.revolut.repository.impl.TransactionRepository;
import test.revolut.repository.impl.UserRepository;
import test.revolut.service.AccountService;
import test.revolut.service.TransactionService;
import test.revolut.service.UserService;
import test.revolut.service.impl.AccountServiceImpl;
import test.revolut.service.impl.TransactionServiceImpl;
import test.revolut.service.impl.UserServiceImpl;

public class ApplicationBinder extends AbstractBinder {
    @Override
        protected void configure() {
    	
        bind(TransactionServiceImpl.class).to(TransactionService.class);
        bind(UserServiceImpl.class).to(UserService.class); 
        bind(AccountServiceImpl.class).to(AccountService.class); 
         
        
        bind(UserRepository.class).to(UserRepository.class);
        bind(TransactionRepository.class).to(TransactionRepository.class);
        bind(AccountRepository.class).to(AccountRepository.class);
     
            
    }
}