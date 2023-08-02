package la.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import la.bean.CategoryBean;

/**
 * ItemDAOのテストクラス
 */
class ItemDAOTest {

	/** テスト対象クラス：syystem under test */
	ItemDAO sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut =  new ItemDAO();
	}
	
	@Nested
	@DisplayName("ItemDAO#findAllCategoryメソッドのテストクラス")
	class FindAllCategoryTest {
		@Test
		@DisplayName("【Test-11】カテゴリーテーブルの全カテゴリーを取得できる")
		void test11() throws Exception {
			// setup
			List<CategoryBean> expected = new ArrayList<CategoryBean>();
			expected.add(new CategoryBean(1, "本"));
			expected.add(new CategoryBean(2, "DVD"));
			expected.add(new CategoryBean(3, "ゲーム"));
			// execute
			List<CategoryBean> actual = sut.findAllCategory();
			// verify
			if (actual.size() == 0) {
				fail("テスト対象メソッドはまだ実装されていません。");
			} else {
				// TODO シリアライズ化メソッドでの比較ではなく独自Matcherによる比較でもよい
				for (int i = 0; i < actual.size(); i++) {
					assertThat(actual.get(i).toString(), is(expected.get(i).toString()));
				}
			}
		}
	}
	
	@Nested
	@DisplayName("ItemDAO#constructorメソッドのテストクラス")
	class ConstructorTest {
		@Test
		@DisplayName("【Test-02】ItemDAOクラスのconフィールドはインスタンス化できる")
		void test02() {
			// execute
			Connection actual = sut.getCon();
			// verify
			assertThat(actual, is(instanceOf(Connection.class)));
		}
		@Test
		@DisplayName("【Test-01】ItemDAOクラスをインスタンス化できる")
		void test01() throws Exception {
			// execute & verify
			assertThat(new ItemDAO(), is(instanceOf(ItemDAO.class)));
		}
	}

}
