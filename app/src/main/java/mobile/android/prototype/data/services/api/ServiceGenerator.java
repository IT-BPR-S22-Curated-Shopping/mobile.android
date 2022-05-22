//package mobile.android.prototype.data.services.api;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class ServiceGenerator {
//
//
//
//    private static final Retrofit.Builder getRetrofitBuilder = new Retrofit.Builder()
//            .baseUrl("https://url:9000")
//            .addConverterFactory(GsonConverterFactory.create());
//
//    private static final Retrofit retrofit = getRetrofitBuilder.build();
//    private static final ApiProvider apiService = retrofit.create(ApiProvider.class);
//
//    public static ApiProvider getApi() {
//        return apiService;
//    }
//}
