insert into entry
(
agency_id, estate_id, price
)
SELECT 1, estate_id, 1 FROM estate WHERE NOT EXISTS (SELECT 1 FROM entry e WHERE e.estate_id = estate.estate_id)

-- 1차 실행
-- 1701/1706 개