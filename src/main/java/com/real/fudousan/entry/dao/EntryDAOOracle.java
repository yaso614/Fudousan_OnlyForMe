package com.real.fudousan.entry.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.real.fudousan.entry.vo.Entry;
import com.real.fudousan.estate.service.EstateService;

@Repository
public class EntryDAOOracle implements EntryDAO {
	 private static final Logger logger = LoggerFactory.getLogger(EstateService.class);

	   @Autowired
	   private SqlSession session;
	   
	   
	   @Override
	   public int updateByIds(Entry entry){
		   
		   int result = 0;
		      
		      try{
		        
		         EntryMapper mapper= session.getMapper(EntryMapper.class);
		         result = mapper.updateByIds(entry);
		      }
		      catch(Exception e){
		         e.printStackTrace();
		      }
		      
		      return result;
			  
	   }
	   
	@Override
	public int insertEntry(Entry entry) {
		// TODO Auto-generated method stub
		 int result = 0;
	      
	      try{
	        
	         EntryMapper mapper= session.getMapper(EntryMapper.class);
	         result = mapper.insertEntry(entry);
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }
	      
	      return result;
		
		
	}
	
	
	@Override
	public Entry listEntry(int estateId){
		Entry result = null;
		try{      
			EntryMapper mapper= session.getMapper(EntryMapper.class);
        result = mapper.listEntry(estateId);
     }
     catch(Exception e){
        e.printStackTrace();
     }
     
     return result;
	
	
}

	//매물 삭제 
	  @Override
		public int deleteEntry(Entry entry){
		   int result = 0;
		      
		      try{
		         EntryMapper mapper = session.getMapper(EntryMapper.class);
		        // EntryMapper mapper2= session.getMapper(EntryMapper.class);
		         result = mapper.deleteEntry(entry);
		      }
		      catch(Exception e){
		         e.printStackTrace();
		      }
		      
		      return result;
		   }
	  //매물 수정페이지에 매물 정보 가져오기 
	  @Override
	  public int modifyEstatePage(Entry entry){
		  int result = 0;
		  
		  try{
		         EntryMapper mapper = session.getMapper(EntryMapper.class);
		        // EntryMapper mapper2= session.getMapper(EntryMapper.class);
		         result = mapper.modifyEstatePage(entry);
		      }
		      catch(Exception e){
		         e.printStackTrace();
		      }
		      
		      return result;
		
	     }
	
     }
