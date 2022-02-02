package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.math.*;

public class Yacht extends JFrame {
	String list[] = { "Aces", "Dueces", "Threes", "Fours", "Fives", "Sixes", "Bonus", "Chance", "4 of a Kind",
			"Full House", "S. Straight", "L. Straight", "Yacht" };
	Container contentPane;
	JPanel scorePanel;
	JPanel dicePanel;
	JPanel infoPanel;
	JLabel[] listLabel = new JLabel[13];
	JLabel[] scoreLabelP1 = new JLabel[13];
	JLabel[] scoreLabelP2 = new JLabel[13];
	JButton[] selectBtn = new JButton[13];
	JLabel p1Total = new JLabel("0");
	JLabel p2Total = new JLabel("0");

	ImageIcon[] diceImage = { new ImageIcon("C:\\JavaImages\\Dice\\diceOne.png"),
			new ImageIcon("C:\\JavaImages\\Dice\\diceTwo.png"), new ImageIcon("C:\\JavaImages\\Dice\\diceThree.png"),
			new ImageIcon("C:\\JavaImages\\Dice\\diceFour.png"), new ImageIcon("C:\\JavaImages\\Dice\\diceFive.png"),
			new ImageIcon("C:\\JavaImages\\Dice\\diceSix.png") };
	ImageIcon diceBaseImage = new ImageIcon("C:\\JavaImages\\Dice\\diceBase.png");
	ImageIcon lockImage[] = { new ImageIcon("C:\\JavaImages\\Lock\\unlocked.png"),
			new ImageIcon("C:\\JavaImages\\Lock\\locked.png") };

	YachtList p1 = new YachtList("Player 1");
	YachtList p2 = new YachtList("Player 2");
	int currentPlayer = 1;
	int diceRoll = 0;
	JButton diceRollBtn = new JButton("Roll");
	JLabel[] diceLabel = new JLabel[5];
	JLabel[] lockLabel = new JLabel[5];
	int dice[] = { 0, 0, 0, 0, 0 };
	boolean diceLock[] = { false, false, false, false, false };
	JButton diceLockBtn[] = new JButton[5];

	JTextArea consoleInfo = new JTextArea("");
	JLabel playerInfo = new JLabel(p1.getUsername());
	JLabel bonusInfo = new JLabel(p1.getBonus() + "/63");
	JLabel scoreInfo = new JLabel(p1.totalScore() + "");
	JLabel diceInfo = new JLabel("Roll to Start");
	JLabel winnerInfo = new JLabel();

	Yacht() {
		setTitle("¾ßÃß");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		JPanel mainPanel = new JPanel(new GridLayout(2, 1, 8, 24));
		contentPane.setLayout(new BorderLayout(24, 24));
		scorePanel = new JPanel(new GridLayout(15, 4, 3, 3));
		dicePanel = new JPanel(new BorderLayout(16, 16));
		JPanel diceControlPanel = new JPanel(new BorderLayout(16, 16));
		JPanel diceImagePanel = new JPanel(new GridLayout(1, 5, 3, 3));
		JPanel diceSelectPanel = new JPanel(new GridLayout(1, 5, 3, 3));
		JPanel diceLockPanel = new JPanel(new GridLayout(1, 5, 3, 3));
		infoPanel = new JPanel(new GridLayout(1, 2, 48, 0));
		contentPane.setBackground(new Color(0x00242424));
		mainPanel.setBackground(new Color(0x00242424));

		Font sf = new Font("Grandview", Font.PLAIN, 12);
		JLabel ql1 = new JLabel("Categories");
		scorePanel.add(ql1);
		ql1.setFont(sf);
		JLabel ql2 = new JLabel(p1.getUsername());
		scorePanel.add(ql2);
		ql2.setFont(sf);
		JLabel ql3 = new JLabel(p2.getUsername());
		scorePanel.add(ql3);
		ql3.setFont(sf);
		scorePanel.add(new JLabel());
		scorePanel.setBackground(new Color(0x00fafafa));
		for (int i = 0; i < 13; i++) {
			listLabel[i] = new JLabel(list[i]);
			scoreLabelP1[i] = new JLabel(p1.score[i] + "");
			scoreLabelP2[i] = new JLabel(p2.score[i] + "");
			selectBtn[i] = new JButton("Select");
			listLabel[i].setFont(sf);
			scoreLabelP1[i].setFont(sf);
			scoreLabelP2[i].setFont(sf);
			selectBtn[i].setFont(sf);
			selectBtn[i].addActionListener(new SelectListener(i));
			scorePanel.add(listLabel[i]);
			scorePanel.add(scoreLabelP1[i]);
			scorePanel.add(scoreLabelP2[i]);
			if (i != 6) {
				scorePanel.add(selectBtn[i]);
			} else {
				scorePanel.add(new JLabel());
			}
		}
		JLabel ql4 = new JLabel("Total");
		scorePanel.add(ql4);
		ql4.setFont(sf);
		scorePanel.add(p1Total);
		scorePanel.add(p2Total);
		p1Total.setFont(new Font("Grandview", Font.BOLD, 12));
		p2Total.setFont(new Font("Grandview", Font.BOLD, 12));

		scorePanel.add(new JLabel());
		Font df = new Font("Grandview", Font.PLAIN, 16);
		for (int i = 0; i < 5; i++) {
			diceLabel[i] = new JLabel(diceBaseImage);
			diceImagePanel.add(diceLabel[i]);
			diceLockBtn[i] = new JButton("Lock");
			diceLockBtn[i].setFont(df);
			diceSelectPanel.add(diceLockBtn[i]);
			lockLabel[i] = new JLabel(lockImage[0]);
			diceLockPanel.add(lockLabel[i]);
			diceLockBtn[i].addActionListener(new LockListener(i));
		}
		diceControlPanel.add(diceImagePanel, BorderLayout.CENTER);
		diceControlPanel.add(diceSelectPanel, BorderLayout.SOUTH);
		diceControlPanel.add(diceLockPanel, BorderLayout.NORTH);
		dicePanel.add(diceControlPanel, BorderLayout.CENTER);
		diceRollBtn.addActionListener(new RollListener());
		diceRollBtn.setFont(df);
		dicePanel.add(diceRollBtn, BorderLayout.EAST);
		Color dg = new Color(0x00484848);
		diceImagePanel.setBackground(dg);
		diceSelectPanel.setBackground(dg);
		diceLockPanel.setBackground(dg);
		diceControlPanel.setBackground(dg);
		dicePanel.setBackground(dg);

		JPanel infoLPanel = new JPanel();
		infoLPanel.setLayout(new GridLayout(5, 1));
		JPanel infoMiniPanel[] = new JPanel[4];
		String infoList[] = { "Player:", "Bonus:", "Score:", "Dice:" };
		Color ig = new Color(0x00cacaca);
		Font nf = new Font("Grandview", Font.PLAIN, 20);
		infoLPanel.setBackground(ig);
		for (int i = 0; i < 4; i++) {
			infoMiniPanel[i] = new JPanel();
			infoMiniPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 3, 10));
			JLabel qLabel = new JLabel(infoList[i]);
			infoMiniPanel[i].add(qLabel);
			qLabel.setFont(nf);
			infoMiniPanel[i].setBackground(ig);
			infoLPanel.add(infoMiniPanel[i]);
		}
		infoMiniPanel[0].add(playerInfo);
		infoMiniPanel[1].add(bonusInfo);
		infoMiniPanel[2].add(scoreInfo);
		infoMiniPanel[3].add(diceInfo);
		infoPanel.setBackground(ig);
		playerInfo.setFont(nf);
		bonusInfo.setFont(nf);
		scoreInfo.setFont(nf);
		diceInfo.setFont(nf);
		winnerInfo.setFont(nf);
		infoLPanel.add(winnerInfo);
		infoPanel.add(infoLPanel);
		infoPanel.add(new JScrollPane(consoleInfo));
		consoleInfo.setEditable(false);
		consoleInfo.setBorder(new EmptyBorder(10, 10, 10, 20));
		consoleInfo.setBackground(Color.BLACK);
		consoleInfo.setForeground(new Color(0x00acff47));

		contentPane.add(scorePanel, BorderLayout.WEST);
		mainPanel.add(dicePanel);
		mainPanel.add(infoPanel);
		contentPane.add(mainPanel);
		setSize(1000, 600);
		setVisible(true);
		consoleInfo.append("Game Start!\n");
	}

	public class SelectListener implements ActionListener {
		int selectedLabel;

		SelectListener(int a) {
			this.selectedLabel = a;
		}

		public void actionPerformed(ActionEvent e) {
			if (diceRoll >= 1) {
				if (currentPlayer == 1) {
					if (!p1.isfull[selectedLabel]) {
						p1.setScore(selectedLabel, dice);
						consoleInfo.append(p1.getUsername()+" : " + list[selectedLabel] + " (" + p1.getScore(selectedLabel) + ")\n");
						if (p1.isBonus()) {
							p1.setScore(6, 35);
						}
						currentPlayer = 2;
						p1Total.setText(p1.totalScore() + "");
						for (int i = 0; i < 13; i++) {
							scoreLabelP1[i].setText(p1.getScore(i) + "");
							scoreLabelP1[i].setForeground(Color.BLACK);
						}
						diceRoll = 0;
						for (int i = 0; i < 5; i++) {
							dice[i] = 0;
							diceLock[i] = false;
							diceLockBtn[i].setText("Lock");
							diceLabel[i].setIcon(diceBaseImage);
							lockLabel[i].setIcon(lockImage[0]);
						}
						playerInfo.setText(p2.getUsername());
						bonusInfo.setText(p2.getBonus() + "/63");
						if (p2.isBonus()) {
							bonusInfo.setForeground(new Color(0x0024caac));
						} else {
							bonusInfo.setForeground(Color.BLACK);
						}
						scoreInfo.setText(p2.totalScore() + "");
						diceInfo.setText("Roll to Continue");
					}
				} else {
					if (!p2.isfull[selectedLabel]) {
						p2.setScore(selectedLabel, dice);
						consoleInfo.append(p2.getUsername()+" : " + list[selectedLabel] + " (" + p2.getScore(selectedLabel) + ")\n");
						if (p2.isBonus()) {
							p2.setScore(6, 35);
						}
						currentPlayer = 1;
						p2Total.setText(p2.totalScore() + "");
						for (int i = 0; i < 13; i++) {
							scoreLabelP2[i].setText(p2.getScore(i) + "");
							scoreLabelP2[i].setForeground(Color.BLACK);
						}
						diceRoll = 0;
						for (int i = 0; i < 5; i++) {
							dice[i] = 0;
							diceLock[i] = false;
							diceLockBtn[i].setText("Lock");
							diceLabel[i].setIcon(diceBaseImage);
							lockLabel[i].setIcon(lockImage[0]);
						}
						playerInfo.setText(p1.getUsername());
						bonusInfo.setText(p1.getBonus() + "/63");
						if (p1.isBonus()) {
							bonusInfo.setForeground(new Color(0x0024caac));
						} else {
							bonusInfo.setForeground(Color.BLACK);
						}
						scoreInfo.setText(p1.totalScore() + "");
						diceInfo.setText("Roll to Continue");
					}
				}
				if (p1.listFull() && p2.listFull()) {
					playerInfo.setText("None");
					bonusInfo.setText("None");
					scoreInfo.setText("None");
					diceInfo.setText("None");
					if (p1.totalScore() > p2.totalScore()) {
						winnerInfo.setText(p1.getUsername() + " Wins! (+" + (p1.totalScore() - p2.totalScore()) + ")");
						consoleInfo.append(
								p1.getUsername() + " Wins! (" + p1.totalScore() + " : " + p2.totalScore() + ")\n");
					} else if (p2.totalScore() > p1.totalScore()) {
						winnerInfo.setText(p2.getUsername() + " Wins! (+" + (p2.totalScore() - p1.totalScore()) + ")");
						consoleInfo.append(
								p2.getUsername() + " Wins! (" + p1.totalScore() + " : " + p2.totalScore() + ")\n");

					} else {
						winnerInfo.setText("Tie!");
						consoleInfo.append(
								p1.getUsername() + " & " + p2.getUsername() + " Tie! (" + p1.totalScore() + ")\n");
					}
					consoleInfo.append("Game Over!");
				}
			}
			else {
				consoleInfo.append("Did not roll the dice yet.\n");
			}
		}
	}

	public class LockListener implements ActionListener {
		int selectedLabel;

		LockListener(int a) {
			this.selectedLabel = a;
		}

		public void actionPerformed(ActionEvent e) {
			if (diceRoll >= 1) {
				if (!diceLock[selectedLabel]) {
					diceLockBtn[selectedLabel].setText("Unlock");
					lockLabel[selectedLabel].setIcon(lockImage[1]);
					diceLock[selectedLabel] = true;
				} else {
					diceLockBtn[selectedLabel].setText("Lock");
					lockLabel[selectedLabel].setIcon(lockImage[0]);
					diceLock[selectedLabel] = false;
				}
			} else {
				consoleInfo.append("Did not roll the dice yet.\n");
			}
		}
	}

	public class RollListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (diceRoll <= 2) {
				String str = "";
				for (int i = 0; i < 5; i++) {
					if (!diceLock[i]) {
						dice[i] = (int) (Math.random() * 6) + 1;
						diceLabel[i].setIcon(diceImage[dice[i] - 1]);
					}
					str += (Integer.toString(dice[i]) + " ");
				}
				Color playerColor = new Color(0x0024caac);
				if (currentPlayer == 1) {
					if (p1.isYacht(dice)) {
						str += " (Yacht)";
					} else if (p1.isFourOfAKind(dice)) {
						str += " (Four of a Kind)";
					} else if (p1.isFullHouse(dice)) {
						str += " (Full House)";
					} else if (p1.isLgStraight(dice)) {
						str += " (Large Straight)";
					} else if (p1.isSmStraight(dice)) {
						str += " (Small Striaght)";
					}
					consoleInfo.append(p1.getUsername() + " : " + str + "\n");
					diceInfo.setText(str);
					for (int i = 0; i < 13; i++) {
						if (!p1.isfull[i]) {
							scoreLabelP1[i].setForeground(playerColor);
							scoreLabelP1[i].setText(p1.getScore(i, dice) + "");
						}
					}
				} else {
					if (p2.isYacht(dice)) {
						str += " (Yacht)";
					} else if (p2.isFourOfAKind(dice)) {
						str += " (Four of a Kind)";
					} else if (p2.isFullHouse(dice)) {
						str += " (Full House)";
					} else if (p2.isLgStraight(dice)) {
						str += " (Large Straight)";
					} else if (p2.isSmStraight(dice)) {
						str += " (Small Striaght)";
					}
					consoleInfo.append(p2.getUsername() + " : " + str + "\n");
					diceInfo.setText(str);
					for (int i = 0; i < 13; i++) {
						if (!p2.isfull[i]) {
							scoreLabelP2[i].setForeground(playerColor);
							scoreLabelP2[i].setText(p2.getScore(i, dice) + "");
						}
					}
				}
				diceRoll++;
			} else {
				consoleInfo.append("Already rolled 3 times.\n");
			}
		}
	}

	public class YachtList {
		int score[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		boolean isfull[] = { false, false, false, false, false, false, true, false, false, false, false, false, false };
		String user;

		YachtList() {
			this.user = "User";
		}

		YachtList(String username) {
			this.user = username;
		}

		public void setUsername(String username) {
			this.user = username;
		}

		public String getUsername() {
			return user;
		}

		public int scoreAces(int num[]) {
			int total = 0;
			for (int i = 0; i < 5; i++) {
				if (num[i] == 1) {
					total += 1;
				}
			}
			return total;
		}

		public int scoreDueces(int num[]) {
			int total = 0;
			for (int i = 0; i < 5; i++) {
				if (num[i] == 2) {
					total += 2;
				}
			}
			return total;
		}

		public int scoreThrees(int num[]) {
			int total = 0;
			for (int i = 0; i < 5; i++) {
				if (num[i] == 3) {
					total += 3;
				}
			}
			return total;
		}

		public int scoreFours(int num[]) {
			int total = 0;
			for (int i = 0; i < 5; i++) {
				if (num[i] == 4) {
					total += 4;
				}
			}
			return total;
		}

		public int scoreFives(int num[]) {
			int total = 0;
			for (int i = 0; i < 5; i++) {
				if (num[i] == 5) {
					total += 5;
				}
			}
			return total;
		}

		public int scoreSixes(int num[]) {
			int total = 0;
			for (int i = 0; i < 5; i++) {
				if (num[i] == 6) {
					total += 6;
				}
			}
			return total;
		}

		public int getBonus() {
			int total = 0;
			for (int i = 0; i < 6; i++) {
				total += score[i];
			}
			return total;
		}

		public int scoreBonus() {
			int total = 0;
			for (int i = 0; i < 6; i++) {
				total += score[i];
			}
			if (total >= 63) {
				return 35;
			} else {
				return 0;
			}
		}

		public boolean isBonus() {
			int total = 0;
			for (int i = 0; i < 6; i++) {
				total += score[i];
			}
			if (total >= 63) {
				return true;
			} else {
				return false;
			}
		}

		public int scoreChance(int num[]) {
			int total = 0;
			for (int i = 0; i < 5; i++) {
				total += num[i];
			}
			return total;
		}

		public int scoreFourOfAKind(int num[]) {
			int diceNum[] = new int[6];
			int total = 0;
			for (int i = 0; i < 5; i++) {
				total += num[i];
				diceNum[num[i] - 1]++;
			}
			boolean isFourOfAKind = false;
			for (int i = 0; i < 6; i++) {
				if (diceNum[i] >= 4) {
					isFourOfAKind = true;
					break;
				}
			}
			if (isFourOfAKind) {
				return total;
			} else {
				return 0;
			}
		}

		public boolean isFourOfAKind(int num[]) {
			int diceNum[] = new int[6];
			for (int i = 0; i < 5; i++) {
				diceNum[num[i] - 1]++;
			}
			boolean isFourOfAKind = false;
			for (int i = 0; i < 6; i++) {
				if (diceNum[i] >= 4) {
					isFourOfAKind = true;
					break;
				}
			}
			return isFourOfAKind;
		}

		public int scoreFullHouse(int num[]) {
			int diceNum[] = new int[6];
			int total = 0;
			for (int i = 0; i < 5; i++) {
				total += num[i];
				diceNum[num[i] - 1]++;
			}
			for (int i = 0; i < 6; i++) {
				for (int j = 5; j > i; j--) {
					if (diceNum[j - 1] <= diceNum[j]) {
						int n = diceNum[j - 1];
						diceNum[j - 1] = diceNum[j];
						diceNum[j] = n;
					}
				}
			}
			if (diceNum[0] == 5) {
				return total;
			} else {
				if (diceNum[0] == 3 && diceNum[1] == 2) {
					return total;
				} else {
					return 0;
				}
			}
		}

		public boolean isFullHouse(int num[]) {
			int diceNum[] = new int[6];
			for (int i = 0; i < 5; i++) {
				diceNum[num[i] - 1]++;
			}
			for (int i = 0; i < 6; i++) {
				for (int j = 5; j > i; j--) {
					if (diceNum[j - 1] <= diceNum[j]) {
						int n = diceNum[j - 1];
						diceNum[j - 1] = diceNum[j];
						diceNum[j] = n;
					}
				}
			}
			if (diceNum[0] == 5) {
				return true;
			} else {
				if (diceNum[0] == 3 && diceNum[1] == 2) {
					return true;
				} else {
					return false;
				}
			}
		}

		public int scoreSmStraight(int num[]) {
			int diceNum[] = new int[5];
			for (int i = 0; i < 5; i++) {
				diceNum[i] = num[i];
			}
			for (int i = 0; i < 5; i++) {
				for (int j = 4; j > i; j--) {
					if (diceNum[j - 1] >= diceNum[j]) {
						int n = diceNum[j - 1];
						diceNum[j - 1] = diceNum[j];
						diceNum[j] = n;
					}
				}
			}
			boolean isShStraight = true;
			boolean skippedNum = false;
			int cur = diceNum[0];
			for (int i = 0; i < 5; i++) {
				if (diceNum[i] == cur) {
					cur++;
				} else {
					if (!skippedNum && diceNum[i] <= cur) {
						skippedNum = true;
					} else {
						isShStraight = false;
					}
				}
			}
			if (isShStraight) {
				return 15;
			} else {
				return 0;
			}
		}

		public boolean isSmStraight(int num[]) {
			int diceNum[] = new int[5];
			for (int i = 0; i < 5; i++) {
				diceNum[i] = num[i];
			}
			for (int i = 0; i < 5; i++) {
				for (int j = 4; j > i; j--) {
					if (diceNum[j - 1] >= diceNum[j]) {
						int n = diceNum[j - 1];
						diceNum[j - 1] = diceNum[j];
						diceNum[j] = n;
					}
				}
			}
			boolean isShStraight = true;
			boolean skippedNum = false;
			int cur = diceNum[0];
			for (int i = 0; i < 5; i++) {
				if (diceNum[i] == cur) {
					cur++;
				} else {
					if (!skippedNum && diceNum[i] <= cur) {
						skippedNum = true;
					} else {
						isShStraight = false;
					}
				}
			}
			return isShStraight;
		}

		public int scoreLgStraight(int num[]) {
			int diceNum[] = new int[5];
			for (int i = 0; i < 5; i++) {
				diceNum[i] = num[i];
			}
			for (int i = 0; i < 5; i++) {
				for (int j = 4; j > i; j--) {
					if (diceNum[j - 1] >= diceNum[j]) {
						int n = diceNum[j - 1];
						diceNum[j - 1] = diceNum[j];
						diceNum[j] = n;
					}
				}
			}
			boolean isLgStraight = true;
			int cur = diceNum[0];
			for (int i = 0; i < 5; i++) {
				if (diceNum[i] == cur) {
					cur++;
				} else {
					isLgStraight = false;
				}
			}
			if (isLgStraight) {
				return 30;
			} else {
				return 0;
			}
		}

		public boolean isLgStraight(int num[]) {
			int diceNum[] = new int[5];
			for (int i = 0; i < 5; i++) {
				diceNum[i] = num[i];
			}
			for (int i = 0; i < 5; i++) {
				for (int j = 4; j > i; j--) {
					if (diceNum[j - 1] >= diceNum[j]) {
						int n = diceNum[j - 1];
						diceNum[j - 1] = diceNum[j];
						diceNum[j] = n;
					}
				}
			}
			boolean isLgStraight = true;
			int cur = diceNum[0];
			for (int i = 0; i < 5; i++) {
				if (diceNum[i] == cur) {
					cur++;
				} else {
					isLgStraight = false;
				}
			}
			if (isLgStraight) {
				return true;
			} else {
				return false;
			}
		}

		public int scoreYacht(int num[]) {
			if ((num[0] == num[1]) && (num[1] == num[2]) && (num[2] == num[3]) && (num[3] == num[4])) {
				return 50;
			} else {
				return 0;
			}
		}

		public boolean isYacht(int num[]) {
			if ((num[0] == num[1]) && (num[1] == num[2]) && (num[2] == num[3]) && (num[3] == num[4])) {
				return true;
			} else {
				return false;
			}
		}

		public int totalScore() {
			int total = 0;
			for (int i = 0; i < score.length; i++) {
				total += score[i];
			}
			return total;
		}

		public int getScore(int n) {
			return this.score[n];
		}

		public int getScore(int n, int num[]) {
			if (n == 0) {
				return this.scoreAces(num);
			} else if (n == 1) {
				return this.scoreDueces(num);
			} else if (n == 2) {
				return this.scoreThrees(num);
			} else if (n == 3) {
				return this.scoreFours(num);
			} else if (n == 4) {
				return this.scoreFives(num);
			} else if (n == 5) {
				return this.scoreSixes(num);
			} else if (n == 6) {
				return this.scoreBonus();
			} else if (n == 7) {
				return this.scoreChance(num);
			} else if (n == 8) {
				return this.scoreFourOfAKind(num);
			} else if (n == 9) {
				return this.scoreFullHouse(num);
			} else if (n == 10) {
				return this.scoreSmStraight(num);
			} else if (n == 11) {
				return this.scoreLgStraight(num);
			} else if (n == 12) {
				return this.scoreYacht(num);
			} else {
				return 0;
			}
		}

		public void setScore(int n, int a) {
			this.score[n] = a;
			isfull[n] = true;
		}

		public void setScore(int n, int num[]) {
			if (n == 0) {
				this.score[n] = this.scoreAces(num);
			} else if (n == 1) {
				this.score[n] = this.scoreDueces(num);
			} else if (n == 2) {
				this.score[n] = this.scoreThrees(num);
			} else if (n == 3) {
				this.score[n] = this.scoreFours(num);
			} else if (n == 4) {
				this.score[n] = this.scoreFives(num);
			} else if (n == 5) {
				this.score[n] = this.scoreSixes(num);
			} else if (n == 6) {
				this.score[n] = this.scoreBonus();
			} else if (n == 7) {
				this.score[n] = this.scoreChance(num);
			} else if (n == 8) {
				this.score[n] = this.scoreFourOfAKind(num);
			} else if (n == 9) {
				this.score[n] = this.scoreFullHouse(num);
			} else if (n == 10) {
				this.score[n] = this.scoreSmStraight(num);
			} else if (n == 11) {
				this.score[n] = this.scoreLgStraight(num);
			} else if (n == 12) {
				this.score[n] = this.scoreYacht(num);
			}
			isfull[n] = true;
		}

		public boolean listFull() {
			boolean listFull = true;
			for (int i = 0; i < 13; i++) {
				if (i != 7) {
					if (!isfull[i]) {
						listFull = false;
						break;
					}
				}
			}
			return listFull;
		}
	}

	public static void main(String[] args) {
		new Yacht();
	}

}
