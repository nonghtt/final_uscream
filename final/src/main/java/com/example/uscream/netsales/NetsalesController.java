package com.example.uscream.netsales;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uscream.monthlypay.MonthlypayDto;
import com.example.uscream.monthlypay.MonthlypayService;
import com.example.uscream.porder.PorderService;
import com.example.uscream.selling.SellingService;
import com.example.uscream.store.Store;
import com.example.uscream.store.StoreDto;
import com.example.uscream.store.StoreService;

@RestController
@RequestMapping("/netsales")
@CrossOrigin(origins = "*")
@EnableScheduling
public class NetsalesController {

	@Autowired
	NetsalesService NetsalesService;

	@Autowired
	SellingService SellingService;

	@Autowired
	MonthlypayService MonthlypayService;

	@Autowired
	PorderService PorderService;

	@Autowired
	StoreService StoreService;

	@Scheduled(cron = "0 0 0 1 * ?") // 매월 첫째 날 자정에 메서드 실행 실행 (cron = "초 분 시 일")
	public Map addNetsales() {

		LocalDate today = LocalDate.now(); 
		int year = today.getYear();
		int month = today.getMonthValue();

		if (month == 1) { // 2023년 1월에 메서드 실행됐으면 2022년 12월 조회
			month = 12;
			year -= 1;
		} else {
			month -= 1;
		}
		

		Map map = new HashMap();

		ArrayList<StoreDto> storeList = StoreService.getAll();
		NetsalesDto dto = new NetsalesDto();

		for (StoreDto vo : storeList) { // storeid 전체 꺼내옴

			// netsalesdate 추가 (조회월)
			dto.setNetsalesdate(LocalDate.of(year, month, 1));

			// storeid 추가
			dto.setStoreid(new Store(vo.getStoreid(), vo.getStorename(), vo.getPwd(), vo.getManagername(),
					vo.getAccounttype(), vo.getStoreimg(), vo.getX(), vo.getY())); // store 객체 추가

			// storeid별 월별 총매출 추가
			int msellingprice = 0;
			ArrayList<Map<String, Object[]>> salesList = SellingService.getByYearAndMonthSales(vo.getStoreid(), year, month);
			System.out.println(salesList);
			if (!salesList.isEmpty()) {
			    Map<String, Object[]> salesData = salesList.get(0);
			    Object salesDataArray = salesData.get("TOTALPRICE");
			    if (salesDataArray instanceof Integer) {
			        msellingprice = (int) salesDataArray;
			    }
			}

			dto.setMsellingprice(msellingprice);

			
			// storeid별 총급여 추가
			int mpsalary = 0;
			ArrayList<MonthlypayDto> salaryList = MonthlypayService.getByStoreAndMonth(vo.getStoreid(), year, month);
			if (!salaryList.isEmpty()) {
				MonthlypayDto salaryData = salaryList.get(0);
				mpsalary = salaryData.getMpsalary();
			}

			dto.setMpsalary(mpsalary);

			
			// storeid별 월별 총 발주금액 추가
			int mordercost = 0;
			ArrayList<Map<String, String>> orderList = PorderService.getMonthlyOrderCost(vo.getStoreid(), year, month);
			if (!orderList.isEmpty()) {
			    Map<String, String> orderData = orderList.get(0);
			    Object monthlyTotalValueObj = orderData.get("MONTHLY_TOTAL");
			    if (monthlyTotalValueObj instanceof Integer) {
			    	mordercost = (Integer) monthlyTotalValueObj;
			    }
			}

			dto.setMordercost(mordercost);

			System.out.println(dto);
			NetsalesService.save(dto);

		}
		
		return map;
	}

//	private String parseInt(int year) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	

	// 전체 검색
	@GetMapping("")
	public Map getAll() {
		ArrayList<NetsalesDto> list = NetsalesService.getAll();
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	// 선택한 연도의 월별 순매출
	@GetMapping("/{storeid}/{year}")
	public Map getBySelectYear(@PathVariable("storeid") String storeid, @PathVariable("year") int year) {
		ArrayList<Map<String, Object[]>> list = NetsalesService.getBySelectYear(storeid, year);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	// 선택한 연도 및 월의 매출, 인건비, 발주금액, 순매출
	@GetMapping("/{storeid}/{year}/{month}")
	public Map getBySelectYearAndMonth(@PathVariable("storeid") String storeid, @PathVariable("year") int year,
			@PathVariable("month") int month) {
		ArrayList<Map<String, Object[]>> list = NetsalesService.getBySelectYearAndMonth(storeid, year, month);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

}
