package com.tedu.cloudnote.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.NoteDAO;
import com.tedu.cloudnote.dao.ShareDAO;
import com.tedu.cloudnote.entity.Note;
import com.tedu.cloudnote.entity.Share;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Service("noteService")
@Transactional
public class NoteServiceImpl implements NoteService{
	@Resource
	private NoteDAO dao;
	@Resource
	private ShareDAO shareDao;
	
	//搜索笔记
	public NoteResult searchNotes(String title, String status, String begin, String end) {
		NoteResult rst = new NoteResult();
		Map<String, Object> params = new HashMap<String, Object>();
		//标题
		if(title != null && !"".equals(title)) {
			params.put("title", "%"+title+"%");
		}
		//状态
		if(!"0".equals(status) && !"".equals(status)) {
			params.put("status", status);
		}
		//开始日期
		if(begin != null && !"".equals(begin)) {
			Date beginDate = Date.valueOf(begin);
			params.put("begin", beginDate.getTime());
		}
		//结束日期
		if(end != null && !"".equals(end)) {
			Date endDate = Date.valueOf(end);
			params.put("end", endDate.getTime());
		}
		//根据查询参数查询笔记信息
		List<Note> notes = dao.findNotes(params);
		rst.setStatus(0);
		rst.setMsg("搜索笔记完毕");
		rst.setData(notes);
		return rst;
	}
	//预览分享笔记
	public NoteResult loadShareNote(String noteId) {
		NoteResult rst = new NoteResult();
		Share share = shareDao.findByNoteId(noteId);
		if(share != null) {
			rst.setStatus(0);
			rst.setMsg("加载笔记成功");
			rst.setData(share);
			return rst;
		}
		rst.setStatus(1);
		rst.setMsg("加载笔记失败");
		return rst;
	}
	//恢复笔记
	public NoteResult replayNote(String noteId, String bookId) {
		NoteResult rst = new NoteResult();
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_notebook_id(bookId);
		note.setCn_note_status_id("1");
		int rows = dao.move(note);
		if(rows == 1) {
//			dao.updateStatus(note);
			dao.dynamicUpdate(note);
			rst.setStatus(0);
			rst.setMsg("移动笔记成功");
			return rst;
		}
		rst.setStatus(1);
		rst.setMsg("恢复笔记失败");
		return rst;
	}
	//彻底删除笔记
	public NoteResult deleteRollbackNote(String noteId) {
		NoteResult rst = new NoteResult();
		int rows = dao.delete(noteId);
		if(rows >=1) {
			rst.setStatus(0);
			rst.setMsg("彻底删除笔记成功");
			return rst;
		}
		rst.setStatus(1);
		rst.setMsg("彻底删除笔记失败");
		return rst;
	}
	//搜索分享笔记
	public NoteResult searchShareNote(String keyword, int page) {
		NoteResult rst = new NoteResult();
		String title = "%";
		if(keyword != null && !"".equals(keyword)) {
			title = "%"+keyword+"%";
		}
		if(page<1) {
			page = 1;
		}
		//计算抓取起点
		int begin = (page-1)*5;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyword", title); //对应#{keyword}
		params.put("begin", begin); //对应#{begin}
		List<Share> shareNotes = shareDao.findLikeTitle(params);
		rst.setStatus(0);
		rst.setMsg("搜索分享笔记完毕");
		rst.setData(shareNotes);
		return rst;
	}
	//分享笔记
	public NoteResult shareNote(String noteId) {
		NoteResult rst = new NoteResult();
		Note note = dao.findByNoteId(noteId);
		String noteType = note.getCn_note_type_id();
		if("2".equals(noteType)) {
			rst.setStatus(2);
			rst.setMsg("该笔记已分享！");
			return rst;
		}
		Share share = new Share();
		share.setCn_share_id(NoteUtil.createId());
		share.setCn_note_id(noteId);
		share.setCn_share_title(note.getCn_note_title());
		share.setCn_share_body(note.getCn_note_body());
		int rows = shareDao.save(share);
		if(rows == 1) {
			int i = dao.modifyTypeId2(noteId);
			if(i ==1) {
				rst.setStatus(0);
				rst.setMsg("分享笔记成功");
				rst.setData(noteId);
				return rst;
			}
		}
		rst.setStatus(1);
		rst.setMsg("分享笔记失败");
		return rst;
	}
	//加载回收站列表
	public NoteResult loadRollbackNote(String userId){
		NoteResult rst = new NoteResult();
		List<Map> notes = dao.findByStatusId2(userId);
		rst.setStatus(0);
		rst.setMsg("回收站列表加载成功");
		rst.setData(notes);
		return rst;
	}
	//移动笔记
	public NoteResult moveNote(String noteId, String bookId) {
		NoteResult rst = new NoteResult();
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_notebook_id(bookId);
		int rows = dao.move(note);
		if(rows == 1) {
			rst.setStatus(0);
			rst.setMsg("移动笔记成功");
			return rst;
		}
		rst.setStatus(1);
		rst.setMsg("移动笔记失败");
		return rst;
	}
	//删除笔记
	public NoteResult deleteNote(String noteId) {
		NoteResult rst = new NoteResult();
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_note_status_id("2");
		int rows = dao.updateStatus(note);
		if(rows >= 1) {
			rst.setStatus(0);
			rst.setMsg("删除笔记成功");
			return rst;
		}
		rst.setStatus(1);
		rst.setMsg("删除笔记失败");
		return rst;
	}
	
	//添加笔记
	public NoteResult addNote(String userId, String bookId, String title) {
		Note note = new Note();
		note.setCn_user_id(userId); //设置用户ID
		note.setCn_notebook_id(bookId); //设置笔记本ID
		String noteId = NoteUtil.createId();
		note.setCn_note_id(noteId); //设置笔记ID
		note.setCn_note_title(title); //设置笔记标题
		note.setCn_note_create_time(System.currentTimeMillis()); //设置笔记创建时间
		note.setCn_note_last_modify_time(System.currentTimeMillis()); //设置笔记修改时间
		int rows = dao.save(note);
		NoteResult rst = new NoteResult();
		if(rows == 1) {
			rst.setStatus(0);
			rst.setMsg("笔记创建成功");
			rst.setData(noteId); //返回笔记ID
			return rst;
		}
		rst.setStatus(1);
		rst.setMsg("笔记创建失败");
		return rst;
	}
	
	//修改笔记
	public NoteResult modifyNote(String noteId, String title, String body) {
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_note_title(title);
		note.setCn_note_body(body);
		Long time = System.currentTimeMillis();
		note.setCn_note_last_modify_time(time);
//		int rows = dao.modify(note);
		int rows = dao.dynamicUpdate(note);
		NoteResult rst = new NoteResult();
		if(rows == 1) {
			rst.setStatus(0);
			rst.setMsg("笔记保存成功");
			return rst;
		} else {
			rst.setStatus(1);
			rst.setMsg("笔记保存失败");
			return rst;
		}
	}
	//加载笔记列表
	public NoteResult loadBookNotes(String bookId) {
		//按笔记本id查询笔记信息
		List<Note> notes = dao.findByBookId(bookId);
		//创建返回结果
		NoteResult rst = new NoteResult();
		rst.setStatus(0);
		rst.setMsg("查询完毕");
		rst.setData(notes);
		return rst;
	}
	//加载笔记内容
	public NoteResult loadNote(String noteId) {
		Note note = dao.findByNoteId(noteId);
		NoteResult rst = new NoteResult();
		rst.setStatus(0);
		rst.setMsg("查询完毕");
		rst.setData(note);
		return rst;
	}

}
