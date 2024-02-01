package com.act.util;

import com.act.dao.ActDao;
import com.act.model.ActVO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

//排程器
@Service
public class ActStatusUpdateService {

    private final ActDao actDao;

    public ActStatusUpdateService(ActDao actDao) {
        this.actDao = actDao;
    }
    /* @Scheduled
    這個註解是用來定義排程任務。它的 cron 屬性允許你使用類似於 Unix cron
    表達式的格式來設定任務的執行時間。
    每個 cron 表達式都由六或七個時間和日期欄位組成，代表不同的時間單位。
    規則為 秒,分,小時,日,月,星期 */
//    @Scheduled(cron = "0 0 * * * *") // 每小時的開始(第 0分鐘的第 0秒)
    @Transactional
    public void updateActStatuses() {
        List<ActVO> activities = actDao.findAll();
        Date now = new Date();

        for (ActVO act : activities) {
            if (act.getActEndTime() != null && act.getActEndTime().before(now)) {
                act.setActStatus((byte) 1); // 已結束
            } else if (act.getRegStartTime() != null && act.getRegEndTime() != null &&
                    act.getRegStartTime().before(now) && act.getRegEndTime().after(now)) {
                act.setActStatus((byte) 4); // 報名中
            } else {
                act.setActStatus((byte) 3); // 進行中
            }

            actDao.save(act); // 更新資料庫
        }
    }
}
