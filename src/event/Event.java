package event;


import role.Role;

/**
 * 事件接口
 * @author 陈禹铮
 *
 */
public interface Event {

	void trigger(Role role);

	String flag();

	boolean isTriggered();

}

