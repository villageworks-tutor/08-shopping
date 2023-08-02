package la.model;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import la.bean.ItemBean;

/**
 * Cartクラスのテストクラス
 */
class CartTest {

	/** テスト対象クラス：systemm under test */
	Cart sut;
	
	@BeforeEach
	void setUp() {
		sut = new Cart();
	}
	
	@Nested
	@DisplayName("Cart#addメソッドのテストクラス")
	class AddTest {
		@ParameterizedTest
		@MethodSource("provideSource11")
		@DisplayName("【Test-11】カートに商品を追加することができる")
		void test11(List<ItemBean> targets, List<ItemBean> expected) {
			// setup
			// execcute
			for (ItemBean target : targets) {
				sut.add(target);
			}
			// verify
			List<ItemBean> actual = sut.getItems();
			if (actual.size() == 0) {
				fail("テスト対象メソッドはまだ実装されていません。");
			} else {
				for (int i = 0; i < actual.size(); i++) {
					assertThat(actual.get(i).toString(), is(expected.get(i).toString()));
				}
			}
		}
		static Stream<Arguments> provideSource11() {
			// setup
			ItemBean[] items = null;
			List<ItemBean> targetList = null;
			List<List<ItemBean>> targets = new ArrayList<List<ItemBean>>();
			List<ItemBean> expectedList = null;
			List<List<ItemBean>> expected = new ArrayList<List<ItemBean>>();
			//【Test-11.1】空の商品に商品「商品番号：1　商品名：Javaの基本 価格：2500　数量：1」を追加できる
			items = new ItemBean[1];
			items[0] = new ItemBean(1, "Javaの基本", 2500, 1);
			// 実際に追加する商品リストの準備
			targetList = new ArrayList<ItemBean>();
			for (ItemBean item : items) {
				targetList.add(item);
			}
			targets.add(targetList);
			// 期待値リストの準備
			expectedList = new ArrayList<ItemBean>();
			for (ItemBean item : items) {
				expectedList.add(item);
			}
			expected.add(expectedList);
			
			//【Test-11.2】カートに商品「商品番号：1　商品名：Javaの基本 価格：2500　数量：1」がある状態で商品「商品番号：5　商品名：The Racer 価格：1000　数量：2」を追加できる
			items = new ItemBean[2];
			items[0] = new ItemBean(1, "Javaの基本", 2500, 1);
			items[1] = new ItemBean(5, "The Racer", 1000, 2);
			// 実際に追加する商品リストの準備
			targetList = new ArrayList<ItemBean>();
			for (ItemBean item : items) {
				targetList.add(item);
			}
			targets.add(targetList);
			// 期待値リストの準備
			expectedList = new ArrayList<ItemBean>();
			for (ItemBean item : items) {
				expectedList.add(item);
			}
			expected.add(expectedList);
			
			//【Test-11.3】カートに商品「商品番号：1　商品名：Javaの基本 価格：2500　数量：1」と商品「商品番号：5　商品名：The Racer 価格：1000　数量：2」がある状態で商品「商品番号：5　商品名：The Racer 価格：1000　数量：1」を追加できる
			items = new ItemBean[3];
			items[0] = new ItemBean(1, "Javaの基本", 2500, 1);
			items[1] = new ItemBean(5, "The Racer", 1000, 2);
			items[2] = new ItemBean(5, "The Racer", 1000, 1);
			// 実際に追加する商品リストの準備
			targetList = new ArrayList<ItemBean>();
			for (ItemBean item : items) {
				targetList.add(item);
			}
			targets.add(targetList);
			// 期待値リストの準備
			expectedList = new ArrayList<ItemBean>();
			for (int i = 0; i < items.length - 1; i++) {
				if (i == 1) items[i].setQuantity(3); 
				expectedList.add(items[i]);
			}
			expected.add(expectedList);
			
			return Stream.of(
					  // Test-11.1：空のカートに商品を追加できることをテストする
					  Arguments.of(targets.get(0), expected.get(0))
					  // Test-11.2：空ではないカートに商品を追加できることをテストする
					, Arguments.of(targets.get(1), expected.get(1))
					  // Test-11.2：空ではないカートにすでにある商品を追加できることをテストする
					, Arguments.of(targets.get(2), expected.get(2)));
		}
	}

	@Nested
	@DisplayName("Cart#constructorメソッドのテストクラス")
	class ConstructorTest {
		@Test
		@DisplayName("【Test-02】Cartクラスをインスタンス化するとカート内商品リストは初期化される")
		void test02() {
			// execute
			List<ItemBean> actual = sut.getItems();
			// verify
			assertThat(actual, is(instanceOf(ArrayList.class)));
		}
		@Test
		@DisplayName("【Test-01】Cartクラスをインスタンス化できる")
		void test01() {
			// execute & verify
			assertThat(new Cart(), is(instanceOf(Cart.class)));
		}
		
	}

}
