package com.example.uscream.basicschedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/basicschedule")
public class BasicscheduleController {
	@Autowired
	BasicscheduleService service;

	// 기본 스케줄 추가
	// 완!!
	@PostMapping("")
	public Map addBasicschedule(BasicscheduleDto dto) {
		Map map = new HashMap();
		boolean flag = true;
		try {
			dto.setStoreid(dto.getEmp().getStoreid());		// storeid는 emp를 참고해서 넣었다.
			dto.setStatus(0);
			service.save(dto);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("dto", dto);
		map.put("flag", flag);
		return map;
	}

	// 지점별로 스케줄 가져오기
	// 완!
	@GetMapping("/storeid/{storeid}")
	public Map getByStoreid(@PathVariable("storeid") String storeid) {
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<BasicscheduleDto> list = new ArrayList<BasicscheduleDto>();

		try {
			list = service.getByStoreid(storeid);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("list", list);
		map.put("flag", flag);
		return map;
	}

	// pk로 가져오기
	// 완!!
	@GetMapping("/{bsnum}")
	public Map getByBsnum(@PathVariable("bsnum") int bsnum) {
		Map map = new HashMap();
		boolean flag = true;
		BasicscheduleDto dto = new BasicscheduleDto();

		try {
			dto = service.getByBsnum(bsnum);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("dto", dto);
		map.put("flag", flag);
		return map;
	}

	// 직원별로 가져오기
	// 완!!
	@GetMapping("/emp/{empnum}")
	public Map getByEmpnum(@PathVariable("empnum") int empnum) {
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<BasicscheduleDto> list = new ArrayList<BasicscheduleDto>();

		try {
			list = service.getByEmp(empnum,0);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("list", list);
		map.put("flag", flag);
		return map;
	}
	
	// 직원별 + 달 별로 가져오기
	@GetMapping("/emp/{empnum}/{year}/{month}")
	public Map getByEmpnumAndMonth(@PathVariable("empnum") int empnum, @PathVariable("year") int year, @PathVariable("month") int month) {
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<BasicscheduleDto> list = new ArrayList<BasicscheduleDto>();

		try {
			list = service.getByEmpAndMonth(year, month, empnum);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("list", list);
		map.put("flag", flag);
		return map;
	}

	// 기본 스케줄 수정하기 → 날짜, 시작시간, 끝나는시간 수정
	// 완!!
	@PutMapping("")
	public Map edit(BasicscheduleDto dto) {
		Map map = new HashMap();
		boolean flag = true;
		BasicscheduleDto originalDto = service.getByBsnum(dto.getBsnum());
		try {
			originalDto.setBsdate(dto.getBsdate());
			originalDto.setStarttime(dto.getStarttime());
			originalDto.setEndtime(dto.getEndtime());
			service.save(originalDto);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		map.put("dto", dto);
		map.put("flag", flag);
		return map;
	}
	
	// 등록하면 1로 바꿔주기
	@PutMapping("/{bsnum}")
	public Map updateStatus(@PathVariable("bsnum") int bsnum) {
		Map map = new HashMap();
		boolean flag = true;
		try {
			BasicscheduleDto dto = service.getByBsnum(bsnum);
			dto.setStatus(1);
			service.save(dto);
			System.out.println("등록 : "+dto);
		}catch(Exception e) {
			flag = false;
			e.printStackTrace();
		}
		
		map.put("flag", flag);
		return map;
	}

	// 삭제
	// 완!!
	@DeleteMapping("/{bsnum}")
	public Map del(@PathVariable("bsnum") int bsnum) {
		Map map = new HashMap();
		boolean flag = true;

		try {
			service.delete(bsnum);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		map.put("flag", flag);
		return map;
	}
}
