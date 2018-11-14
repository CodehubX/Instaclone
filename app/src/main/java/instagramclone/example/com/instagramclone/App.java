package instagramclone.example.com.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GBWOc6cdKVgIcqTRcF8wJnlDFySidgjT2ryOjBjB")
                // if defined
                .clientKey("x0r9GkMSMwk9uUwd8wBW6PK8cdU5Qh5qAutCSAtk")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
