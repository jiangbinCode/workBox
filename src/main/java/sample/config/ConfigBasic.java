package sample.config;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-14 11:35
 **/
public class ConfigBasic {

    public static final Action ACTION = Action.PRO;
    public static final String VERSION = "内测版:Beta";
    public static final Integer DEF_MASTER_IMG_MAX_NUM = 5;
    public static final Integer DEF_SKU_IMG_MAX_NUM = 24;
    public static final Integer DEF_DETAIL_IMG_MAX_NUM = 99;
    public static final Integer DEF_WHITE_IMG_MAX_NUM = 1;


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
