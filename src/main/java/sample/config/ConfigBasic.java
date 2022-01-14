package sample.config;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-14 11:35
 **/
public class ConfigBasic {

    public static final Action ACTION = Action.DEV;


    public static enum Action {
        DEV {
            @Override
            public String getReqUrl() {
                return "https://127.0.0.1";
            }
        }, PRO {
            @Override
            public String getReqUrl() {
                return "https://acaikeji.zhinengdianzhang.net";
            }
        };


        public abstract String getReqUrl();


    }

}
