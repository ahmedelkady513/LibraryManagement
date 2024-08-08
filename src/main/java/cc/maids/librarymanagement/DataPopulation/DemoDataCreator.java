package cc.maids.librarymanagement.DataPopulation;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DemoDataCreator implements CommandLineRunner {

    private final DemoAccountCreator demoAccountCreator;

    @Override
    public void run(String... args) throws Exception {
        demoAccountCreator.createDemoAccount();
    }
}
