package com.example.uscream.schedule;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.emp.Emp;
import com.example.uscream.store.Store;

@Service
public class ScheduleService {
	@Autowired
	ScheduleDao dao;

	// 추가, 수정
	public ScheduleDto save(ScheduleDto dto) {
		Schedule entity = dao.save(new Schedule(dto.getSnum(), dto.getEmp(), dto.getStoreid(), dto.getSdate(),
				dto.getStarttime(), dto.getEndtime()));
		return new ScheduleDto(entity.getSnum(), entity.getEmp(), entity.getStoreid(), entity.getSdate(),
				entity.getStarttime(), entity.getEndtime());
	}

	// num으로 (pk로) 검색
	public ScheduleDto getBySnum(int snum) {
		Schedule entity = dao.findById(snum).orElse(null);
		if (entity == null) {
			return null;
		} else {
			return new ScheduleDto(entity.getSnum(), entity.getEmp(), entity.getStoreid(), entity.getSdate(),
					entity.getStarttime(), entity.getEndtime());

		}
	}

	// 직원 번호로 검색
	public ArrayList<ScheduleDto> getByEmp(int empnum) {
		Emp e = new Emp(empnum, null, "", null, null, "");
		ArrayList<Schedule> list = dao.findByEmp(e);
		ArrayList<ScheduleDto> dtoList = new ArrayList<ScheduleDto>();
		for (Schedule vo : list) {
			dtoList.add(new ScheduleDto(vo.getSnum(), vo.getEmp(), vo.getStoreid(), vo.getSdate(), vo.getStarttime(),
					vo.getEndtime()));
		}

		return dtoList;
	}

	// 지점 아이디로 검색
	public ArrayList<ScheduleDto> getByStoreid(String storeid) {
		Store s = new Store(storeid, "", "", "", 0, "", 0, 0);
		ArrayList<Schedule> list = dao.findByStoreid(s);
		ArrayList<ScheduleDto> dtoList = new ArrayList<ScheduleDto>();
		for (Schedule vo : list) {
			dtoList.add(new ScheduleDto(vo.getSnum(), vo.getEmp(), vo.getStoreid(), vo.getSdate(), vo.getStarttime(),
					vo.getEndtime()));
		}

		return dtoList;
	}

	// 직원 번호 & 날짜로 검색
	public ArrayList<ScheduleDto> getByEmpAndDate(int empnum, LocalDate sdate) {
		Emp e = new Emp(empnum, null, "", null, null, "");
		ArrayList<Schedule> list = dao.findByEmpAndSdate(e, sdate);
		ArrayList<ScheduleDto> dtoList = new ArrayList<ScheduleDto>();
		for (Schedule vo : list) {
			dtoList.add(new ScheduleDto(vo.getSnum(), vo.getEmp(), vo.getStoreid(), vo.getSdate(), vo.getStarttime(),
					vo.getEndtime()));
		}

		return dtoList;
	}

	// 삭제
	public void delete(int snum) {
		dao.deleteById(snum);
	}
}
