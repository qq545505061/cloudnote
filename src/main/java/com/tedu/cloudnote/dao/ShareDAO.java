package com.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import com.tedu.cloudnote.entity.Share;

public interface ShareDAO {
	public Share findByNoteId(String noteId);
	public List<Share> findLikeTitle(Map<String, Object> params);
	public int save(Share share);
}
