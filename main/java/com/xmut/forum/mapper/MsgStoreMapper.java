package com.xmut.forum.mapper;

import com.xmut.forum.pojo.MsgStore;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface MsgStoreMapper extends Mapper<MsgStore> {

    @Results(id = "resultMap3",value = {
            @Result(property = "id",column = "id"),
            @Result(column = "receiver_uid" ,property = "receiverUid"),
            @Result(column = "sender_uid",property = "senderUid"),
            @Result(column = "msg_content",property = "msgContent"),
            @Result(column = "msg_time",property = "msgTime"),
            @Result(column = "msg_type",property = "msgType" ),
            @Result(column = "msg_isread",property = "msgIsread")
    })
    @Select("select id,receiver_uid,sender_uid,msg_content,msg_time,msg_type,msg_isread from msg_store where" +
            " (receiver_uid = #{receiveruid} or sender_uid = #{receiveruid}) and (sender_uid = #{senderuid} or receiver_uid = #{senderuid}) order by msg_time desc limit 1")
    MsgStore getLastMsg(@Param("receiveruid") String receiveruid, @Param("senderuid") String senderuid);

    @Results(id = "resultMap2",value = {
            @Result(property = "id",column = "id"),
            @Result(column = "receiver_uid" ,property = "receiverUid"),
            @Result(column = "sender_uid",property = "senderUid"),
            @Result(column = "msg_content",property = "msgContent"),
            @Result(column = "msg_time",property = "msgTime"),
            @Result(column = "msg_type",property = "msgType" ),
            @Result(column = "msg_isread",property = "msgIsread")
    })
    @Select("select id,receiver_uid,sender_uid,msg_content,msg_time,msg_type,msg_isread from msg_store where" +
            " (receiver_uid = #{receiveruid} or sender_uid = #{receiveruid}) and (sender_uid = #{senderuid} or receiver_uid = #{senderuid}) order by msg_time desc")
    List<MsgStore> getAllMsg(@Param("receiveruid") String receiveruid, @Param("senderuid") String senderuid);



    //修改消息状态未已读
    @Update("update msg_store set msg_isRead = 1 where id = #{id}")
    void updateMsgReadStatus(@Param("id") int id);
}
