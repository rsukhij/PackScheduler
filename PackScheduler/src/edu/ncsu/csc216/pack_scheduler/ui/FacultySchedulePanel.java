package edu.ncsu.csc216.pack_scheduler.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;


/**
 * Creates a user interface for working with the FacultySchedule.
 * 
 * @author Raymond Dong
 * @author Ethan Taylor
 */
public class FacultySchedulePanel extends JPanel implements ActionListener {
	
    /** ID number used for object serialization. */
    private static final long serialVersionUID = 1L;

    /** JTable for displaying the schedule of Courses */
    private JTable tableSchedule;
    /** JTable for displaying the roll of Students */
    private JTable tableRoll;
    /** TableModel for schedule */
    private ScheduleTableModel scheduleTableModel;
    /** TableModel for roll of Students */
	private StudentRollTableModel studentRollTableModel;
	/** Scroll for student schedule */
	private JScrollPane scrollSchedule;
    /** Scroll pane for table */
	private JScrollPane scrollStudentRoll;
    /** Border for Schedule */
    private TitledBorder borderSchedule;
    /** Panel for displaying Course Details */
    private JPanel pnlCourseDetails;
    /** Label for Course Details name title */
    private JLabel lblNameTitle = new JLabel("Name: ");
    /** Label for Course Details section title */
    private JLabel lblSectionTitle = new JLabel("Section: ");
    /** Label for Course Details title title */
    private JLabel lblTitleTitle = new JLabel("Title: ");
    /** Label for Course Details instructor title */
    private JLabel lblInstructorTitle = new JLabel("Instructor: ");
    /** Label for Course Details credit hours title */
    private JLabel lblCreditsTitle = new JLabel("Credits: ");
    /** Label for Course Details meeting title */
    private JLabel lblMeetingTitle = new JLabel("Meeting: ");
    /** Label for Course Details enrollment cap title */
    private JLabel lblEnrollmentCapTitle = new JLabel("Enrollment Cap: ");
    /** Label for Course Details open seats title */
    private JLabel lblOpenSeatsTitle = new JLabel("Open Seats: ");
    /** Label for Course Details waitlist title */
    private JLabel lblWaitlistTitle = new JLabel("Waitlist: ");
    /** Label for Course Details name */
    private JLabel lblName = new JLabel("");
    /** Label for Course Details section */
    private JLabel lblSection = new JLabel("");
    /** Label for Course Details title */
    private JLabel lblTitle = new JLabel("");
    /** Label for Course Details instructor */
    private JLabel lblInstructor = new JLabel("");
    /** Label for Course Details credit hours */
    private JLabel lblCredits = new JLabel("");
    /** Label for Course Details meeting */
    private JLabel lblMeeting = new JLabel("");
    /** Label for Course Details enrollment cap */
    private JLabel lblEnrollmentCap = new JLabel("");
    /** Label for Course Details open seats */
    private JLabel lblOpenSeats = new JLabel("");
    /** Label for waitlist seats */
    private JLabel lblWaitlist = new JLabel("");
    /** Current user */
    private Faculty currentUser;
    /** Course Roll */
    private CourseRoll roll;
    /** Current user's schedule */
    private FacultySchedule schedule;
    /** Student Directory */
    private StudentDirectory studentDirectory;
    
    
    /**
     * Creates the requirements list.
     */
    public FacultySchedulePanel() {
        super(new GridBagLayout());
        
        RegistrationManager manager = RegistrationManager.getInstance();
        currentUser = (Faculty) manager.getCurrentUser();
        CourseCatalog catalog = manager.getCourseCatalog();
        
        
//        JPanel pnlActions = new JPanel();
//        pnlActions.setLayout(new GridLayout(3, 1));
//        JPanel pnlAddRemove = new JPanel();
//        pnlAddRemove.setLayout(new GridLayout(1, 2));
//        pnlAddRemove.add(btnAddCourse);
//        pnlAddRemove.add(btnRemoveCourse);
//        JPanel pnlResetDisplay = new JPanel();
//        pnlResetDisplay.setLayout(new GridLayout(1, 2));
//        pnlResetDisplay.add(btnReset);
//        pnlResetDisplay.add(btnDisplay);
//        JPanel pnlScheduleTitle = new JPanel();
//        pnlScheduleTitle.setLayout(new GridLayout(1, 3));
//        pnlScheduleTitle.add(lblScheduleTitle);
//        pnlScheduleTitle.add(txtScheduleTitle);
//        pnlScheduleTitle.add(btnSetScheduleTitle);
//        pnlActions.add(pnlAddRemove);
//        pnlActions.add(pnlResetDisplay);
//        pnlActions.add(pnlScheduleTitle);
//        
        Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
//        TitledBorder borderActions = BorderFactory.createTitledBorder(lowerEtched, "Actions");
//        pnlActions.setBorder(borderActions);
//        pnlActions.setToolTipText("Scheduler Actions");
                    
        //Set up FacultySchedule table
        scheduleTableModel = new ScheduleTableModel();
        tableSchedule = new JTable(scheduleTableModel) {
            private static final long serialVersionUID = 1L;
            
            /**
             * Set custom tool tips for cells
             * @param e MouseEvent that causes the tool tip
             * @return tool tip text
             */
            public String getToolTipText(MouseEvent e) {
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                int realColumnIndex = convertColumnIndexToModel(colIndex);
                
                return (String) scheduleTableModel.getValueAt(rowIndex, realColumnIndex);
            }
        };
        tableSchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableSchedule.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tableSchedule.setFillsViewportHeight(true);
        tableSchedule.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                String name = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 0).toString();
                String section = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 1).toString();
                Course c = catalog.getCourseFromCatalog(name, section);
                updateCourseDetails(c);
            }
            
        });
        
        scrollSchedule = new JScrollPane(tableSchedule, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollStudentRoll = new JScrollPane(tableSchedule, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        borderSchedule = BorderFactory.createTitledBorder(lowerEtched, "Faculty Schedule");
        scrollSchedule.setBorder(borderSchedule);
        scrollSchedule.setToolTipText("Faculty Schedule");
        
        //Set up Schedule table
        scheduleTableModel = new ScheduleTableModel();
        tableSchedule = new JTable(scheduleTableModel);
        tableSchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableSchedule.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tableSchedule.setFillsViewportHeight(true);
        
        scrollSchedule = new JScrollPane(tableSchedule, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        borderSchedule = BorderFactory.createTitledBorder(lowerEtched, "");
        scrollSchedule.setBorder(borderSchedule);
        
        
        updateTables();
        
        //Set up the course details panel
        pnlCourseDetails = new JPanel();
        pnlCourseDetails.setLayout(new GridLayout(6, 1));
        JPanel pnlName = new JPanel(new GridLayout(1, 4));
        pnlName.add(lblNameTitle);
        pnlName.add(lblName);
        pnlName.add(lblSectionTitle);
        pnlName.add(lblSection);
        
        JPanel pnlTitle = new JPanel(new GridLayout(1, 1));
        pnlTitle.add(lblTitleTitle);
        pnlTitle.add(lblTitle);
        
        JPanel pnlInstructor = new JPanel(new GridLayout(1, 4));
        pnlInstructor.add(lblInstructorTitle);
        pnlInstructor.add(lblInstructor);
        pnlInstructor.add(lblCreditsTitle);
        pnlInstructor.add(lblCredits);
        
        JPanel pnlMeeting = new JPanel(new GridLayout(1, 1));
        pnlMeeting.add(lblMeetingTitle);
        pnlMeeting.add(lblMeeting);
        
        JPanel pnlEnrollment = new JPanel(new GridLayout(1, 4));
        pnlEnrollment.add(lblEnrollmentCapTitle);
        pnlEnrollment.add(lblEnrollmentCap);
        pnlEnrollment.add(lblOpenSeatsTitle);
        pnlEnrollment.add(lblOpenSeats);
        
        JPanel pnlWaitlist = new JPanel(new GridLayout(1, 2));
        pnlWaitlist.add(lblWaitlistTitle);
        pnlWaitlist.add(lblWaitlist);
        
        pnlCourseDetails.add(pnlName);
        pnlCourseDetails.add(pnlTitle);
        pnlCourseDetails.add(pnlInstructor);
        pnlCourseDetails.add(pnlMeeting);
        pnlCourseDetails.add(pnlEnrollment);
        pnlCourseDetails.add(pnlWaitlist);
        
        TitledBorder borderCourseDetails = BorderFactory.createTitledBorder(lowerEtched, "Course Details");
        pnlCourseDetails.setBorder(borderCourseDetails);
        pnlCourseDetails.setToolTipText("Course Details");
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        add(scrollSchedule, c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        add(pnlCourseDetails, c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        add(scrollStudentRoll, c);
        tableSchedule.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String name = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 0).toString();
				String section = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 1).toString();
				Course c = catalog.getCourseFromCatalog(name, section);
				updateCourseDetails(c);
			}
			
		});
       
    }

    /**
     * Performs an action based on the given {@link ActionEvent}.
     * @param e user event that triggers an action.
     */
    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == btnAddCourse) {
//            int row = tableCatalog.getSelectedRow();
//            if (row == -1) {
//                JOptionPane.showMessageDialog(this, "No course selected in the catalog.");
//            } else {
//                try {
//                    if (!RegistrationManager.getInstance().enrollStudentInCourse(catalog.getCourseFromCatalog(tableCatalog.getValueAt(row, 0).toString(), tableCatalog.getValueAt(row, 1).toString()))) {
//                        JOptionPane.showMessageDialog(this, "Course cannot be added to schedule.");
//                    }
//                } catch (IllegalArgumentException iae) {
//                    JOptionPane.showMessageDialog(this, iae.getMessage());
//                }
//            }
//            updateTables();
//        } else if (e.getSource() == btnRemoveCourse) {
//            int row = tableSchedule.getSelectedRow();
//            if (row == -1) {
//                JOptionPane.showMessageDialog(this, "No item selected in the schedule.");
//            } else {
//                if (!RegistrationManager.getInstance().dropStudentFromCourse(catalog.getCourseFromCatalog(tableSchedule.getValueAt(row, 0).toString(), tableSchedule.getValueAt(row, 1).toString()))) {
//                    JOptionPane.showMessageDialog(this, "Cannot drop student from " + tableSchedule.getValueAt(row, 0).toString());
//                }
//            }
//            updateTables();
//        } else if (e.getSource() == btnReset) {
//            RegistrationManager.getInstance().resetSchedule();
//            updateTables();
//        } else if (e.getSource() == btnSetScheduleTitle) {
//            try {
//                schedule.setTitle(txtScheduleTitle.getText()); 
//            } catch (IllegalArgumentException iae) {
//                JOptionPane.showMessageDialog(this, "Invalid title.");
//            }
//            borderSchedule.setTitle(schedule.getTitle());
//        }
        
        this.repaint();
        this.validate();
    }
    
    /**
     * Updates the catalog and schedule tables.
     */
    public void updateTables() {
        scheduleTableModel.updateData();
       
    }
    
    /**
     * Updates the pnlCourseDetails with full information about the most
     * recently selected course.
     */
    private void updateCourseDetails(Course c) {
        if (c != null) {
            lblName.setText(c.getName());
            lblSection.setText(c.getSection());
            lblTitle.setText(c.getTitle());
            lblInstructor.setText(c.getInstructorId());
            lblCredits.setText("" + c.getCredits());
            lblMeeting.setText(c.getMeetingString());
            lblEnrollmentCap.setText("" + c.getCourseRoll().getEnrollmentCap());
            lblOpenSeats.setText("" + c.getCourseRoll().getOpenSeats());
            lblWaitlist.setText("" + c.getCourseRoll().getNumberOnWaitlist());
        }
    }
    
    /**
     * {@link ScheduleTableModel} is the object underlying the {@link JTable} object that displays
     * the list of {@link Course}s to the user.
     * @author Sarah Heckman
     */
    private class ScheduleTableModel extends AbstractTableModel {
        
        /** ID number used for object serialization. */
        private static final long serialVersionUID = 1L;
        /** Column names for the table */
        private String [] columnNames = {"Name", "Section", "Title", "Meeting Days", "Open Seats"};
        /** Data stored in the table */
        private Object [][] data;
        
        /**
         * Constructs the {@link CourseTableModel} by requesting the latest information
         * from the {@link RequirementTrackerModel}.
         */
        public ScheduleTableModel() {
            updateData();
        }

        /**
         * Returns the number of columns in the table.
         * @return the number of columns in the table.
         */
        public int getColumnCount() {
            return columnNames.length;
        }

        /**
         * Returns the number of rows in the table.
         * @return the number of rows in the table.
         */
        public int getRowCount() {
            if (data == null) 
                return 0;
            return data.length;
        }
        
        /**
         * Returns the column name at the given index.
         * @return the column name at the given column.
         */
        public String getColumnName(int col) {
            return columnNames[col];
        }

        /**
         * Returns the data at the given {row, col} index.
         * @return the data at the given location.
         */
        public Object getValueAt(int row, int col) {
            if (data == null)
                return null;
            return data[row][col];
        }
        
        /**
         * Sets the given value to the given {row, col} location.
         * @param value Object to modify in the data.
         * @param row location to modify the data.
         * @param column location to modify the data.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
        
        /**
         * Updates the given model with {@link Course} information from the {@link WolfScheduler}.
         */
        private void updateData() {
        	currentUser = (Faculty) RegistrationManager.getInstance().getCurrentUser();
        	if (currentUser != null) {
        		schedule = currentUser.getSchedule();
        		data = schedule.getScheduledCourses();

        		FacultySchedulePanel.this.repaint();
        		FacultySchedulePanel.this.validate();

            }
        	
        }
    }

	/**
	 * {@link StudentRollTableModel} is the object underlying the {@link JTable} object that displays
	 * the list of Students to the user.
	 * @author Sarah Heckman
	 */
	private class StudentRollTableModel extends AbstractTableModel {
		
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Column names for the table */
		private String [] columnNames = {"First Name", "Last Name", "Student ID"};
		/** Data stored in the table */
		private Object [][] data;
		
		/**
		 * Constructs the {@link StudentRollTableModel} by requesting the latest information
		 * from the {@link RequirementTrackerModel}.
		 */
		public StudentRollTableModel(Course course) {
			updateData(course);
		}

		/**
		 * Returns the number of columns in the table.
		 * @return the number of columns in the table.
		 */
		public int getColumnCount() {
			return columnNames.length;
		}

		/**
		 * Returns the number of rows in the table.
		 * @return the number of rows in the table.
		 */
		public int getRowCount() {
			if (data == null) 
				return 0;
			return data.length;
		}
		
		/**
		 * Returns the column name at the given index.
		 * @return the column name at the given column.
		 */
		public String getColumnName(int col) {
			return columnNames[col];
		}

		/**
		 * Returns the data at the given {row, col} index.
		 * @return the data at the given location.
		 */
		public Object getValueAt(int row, int col) {
			if (data == null)
				return null;
			return data[row][col];
		}
		
		/**
		 * Sets the given value to the given {row, col} location.
		 * @param value Object to modify in the data.
		 * @param row location to modify the data.
		 * @param column location to modify the data.
		 */
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
		
		/**
		 * Updates the given model with {@link Student} information from the {@link CourseRoll}.
		 */
		public void updateData(Course course) {
			int dataIndex = 0;
			studentDirectory = RegistrationManager.getInstance().getStudentDirectory();
        	for (int i = 0; i < studentDirectory.getStudentDirectory().length; i++) {
        		Student student = studentDirectory.getStudentById(studentDirectory.getStudentDirectory()[i][2]);
        		scheduleLoop:
        		for (int j = 0; j < student.getSchedule().getScheduledCourses().length; j++) {
        			if (student.getSchedule().getScheduledCourses()[j][0].equals(course.getName())
        					&& student.getSchedule().getScheduledCourses()[j][1].equals(course.getSection())) {
        				data[dataIndex] = studentDirectory.getStudentDirectory()[i];
        				break scheduleLoop;
        			}
        		}
            }
        	FacultySchedulePanel.this.repaint();
    		FacultySchedulePanel.this.validate();
		}
	}
}
