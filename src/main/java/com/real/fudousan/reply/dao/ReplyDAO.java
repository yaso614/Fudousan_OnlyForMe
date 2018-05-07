package com.real.fudousan.reply.dao;

import java.util.List;

import com.real.fudousan.reply.vo.Reply;

public interface ReplyDAO {
	public int insertReply(Reply reply);
	public List<Reply> selectReply();
	public boolean deleteReply(int replyId);
	public Reply selectOne(int replyId);
	public int updateReply(Reply reply);
	public List<Reply>selectReplyEstate(int estateId);
}
