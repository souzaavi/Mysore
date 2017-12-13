package com.geliddroid.mysore.Apis;

import com.geliddroid.mysore.models.AboutUsList;
import com.geliddroid.mysore.models.CircularList;
import com.geliddroid.mysore.models.DataList;
import com.geliddroid.mysore.models.InstitutionNameList;
import com.geliddroid.mysore.models.MassList;
import com.geliddroid.mysore.models.NewsList;
import com.geliddroid.mysore.models.NotificationList;
import com.geliddroid.mysore.models.OrgDataList;
import com.geliddroid.mysore.models.OrganisationList;
import com.geliddroid.mysore.models.WordofgodList;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/***
 *Created by levin on 12-11-2017.

 */
public interface ApiServices {

    //InterFace for News
    @FormUrlEncoded
    @POST("/testlink/archdiocesan_app/diocesan_news.php")
    Call<NewsList> getNewsList(@Field("updated_at") String updated_at);

    //InterFace for Notification
    @FormUrlEncoded
    @POST("/testlink/archdiocesan_app/diocesan_notification.php")
    Call<NotificationList> getNotificationList(@Field("updated_at") String updated_at);

    //Interface for About Us
    @FormUrlEncoded
    @POST("/testlink/archdiocesan_app/diocesan_about.php")
    Call<AboutUsList> getAboutUsList(@Field("updated_at") String updated_at);

    //MassTimmings
    @FormUrlEncoded
    @POST("/testlink/archdiocesan_app/diocesan_mass.php")
    Call<MassList> getMassList(@Field("updated_at") String updated_at);

    //Institution Names
    @FormUrlEncoded
    @POST("/testlink/archdiocesan_app/diocesan_inst1.php")
    Call<InstitutionNameList> getInstitutionNameList(@Field("updated_at") String updated_at);

    //Institution Detalis
    @FormUrlEncoded
    @POST("/testlink/archdiocesan_app/diocesan_inst2.php")
    Call<DataList> getInstituteDetailsList(@Field("oid") String oid);

    //Organazation List
    @FormUrlEncoded
    @POST("/testlink/archdiocesan_app/diocesan_org.php")
    Call<OrganisationList> getOrgList(@Field("updated_at") String updated_at);

    //Organazation Details
    @FormUrlEncoded
    @POST("/testlink/archdiocesan_app/diocesan_org1.php")
    Call<OrgDataList> getOrgData(@Field("o_id") String o_id);

    //Circular
    @FormUrlEncoded
    @POST("/testlink/archdiocesan_app/diocesan_circular.php")
    Call<CircularList> getCircular(@Field("updated_at") String updated_at);

    //Gospel
    @FormUrlEncoded
    @POST("Welcome")
    Call<WordofgodList> getWordOfGod(@Field("date") String date);
}
