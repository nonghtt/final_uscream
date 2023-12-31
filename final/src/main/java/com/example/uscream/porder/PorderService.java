package com.example.uscream.porder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.product.Product;
import com.example.uscream.store.Store;

@Service
public class PorderService {
	
	@Autowired
	private PorderDao dao;
	
	private ArrayList<PorderDto> change(ArrayList<Porder> list){
		ArrayList<PorderDto> dlist = new ArrayList<PorderDto>();
		for(Porder entity:list) {
			dlist.add(new PorderDto(entity.getTempnum(),entity.getOrdernum(),entity.getStoreid(),entity.getProductnum(),entity.getAmount(),entity.getOrderdate(),entity.getConfirmdate(),entity.getOrdercost(),entity.getCheckconfirm(),"",0));
		}
		return dlist;
	}
	
	public PorderDto edit(PorderDto dto) {
		Porder entity = dao.save(new Porder(dto.getTempnum(),dto.getOrdernum(),dto.getStoreid(),dto.getProductnum(),dto.getAmount(),dto.getOrderdate(),dto.getConfirmdate(),dto.getOrdercost(),dto.getConfirm()));
		dto.setTempnum(entity.getTempnum());
		dto.setOrdernum(entity.getOrdernum());
		dto.setStoreid(entity.getStoreid());
		dto.setProductnum(entity.getProductnum());
		dto.setAmount(entity.getAmount());
		dto.setOrderdate(entity.getOrderdate());
		dto.setConfirmdate(entity.getConfirmdate());
		dto.setConfirm(entity.getCheckconfirm());
		return dto;
	
	}
	
	public int save(PorderDto[] dto) {
		
		for(PorderDto d: dto) {
			Store storeid = new Store(d.getStore(), "", "", "", 0, "", 0, 0);
			Product productnum = new Product(d.getProduct(), "", "","", "", 0, true);
			 dao.save(new Porder(d.getTempnum(),d.getOrdernum(),storeid,productnum,d.getAmount(),d.getOrderdate(),d.getConfirmdate(),d.getOrdercost(),d.getConfirm()));
			
		}
		
		return dto.length;
	}
	
	public PorderDto getById(int tempnum) {
		Porder entity = dao.findById(tempnum).orElse(null);
		PorderDto dto = new PorderDto(entity.getTempnum(),entity.getOrdernum(),entity.getStoreid(),entity.getProductnum(),entity.getAmount(),entity.getOrderdate(),entity.getConfirmdate(),entity.getOrdercost(),entity.getCheckconfirm(),"",0);
		return dto;
		
	}
	
	public ArrayList<PorderDto> getAll(){
		ArrayList<Porder> list = (ArrayList<Porder>) dao.findAll();
		return change(list);
	}
	
	public ArrayList<PorderDto> getByOrderNum(String ordernum){
		ArrayList<Porder> list = dao.findByOrdernumOrderByTempnum(ordernum);
		return change(list);
	}	
	public ArrayList<Map<String, String>> getNotConfirm(){
		
		return  dao.findNotConfirm();
	}	
	public ArrayList<Map<String, String>> getNotConfirmAndStoreid(String storeid){
		
		return  dao.findNotConfirmAndStoreid(storeid);
	}	
	public ArrayList<Map<String, String>> getStoreOrderlist(String store){
		return  dao.findDistinctOrdernumsByStore(store);
	}	
	
	public ArrayList<PorderDto> getStoreOrder(String ordernum, String storeid){
		Store store = new Store(storeid,"","","",0,"", 0,0);
		ArrayList<Porder> list = dao.findByOrdernumAndStoreid(ordernum, store);
		return change(list);
		
	}
	
	public void confirm(int tempnum,int num) {
		dao.confirm(tempnum,num);
	}
	
	public void delete(int tempnum) {
		dao.deleteById(tempnum);
	}
	
	public void updateAmount(int amount,int ordercost, int tempnum) {
		dao.updateamount(tempnum, amount, ordercost);
	}
	
	public ArrayList<Map<String, String>> getOrdernum(){
		return dao.findDistinctOrdernums();
	}
	
	public ArrayList<Map<String, String>> getMonthlyOrderCost(String storeid,int year,int month){
		
		return dao.findMonthlyOrderCost(storeid,year,month);
	}
	
	

}
