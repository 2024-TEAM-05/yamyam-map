package oz.yamyam_map.module.member.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oz.yamyam_map.module.member.entity.Member;
import oz.yamyam_map.module.member.event.SignedUpEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberEventPublisher {
	private final ApplicationEventPublisher eventPublisher;

	@Async
	public void publishSignedUpEvent(Member member) {
		eventPublisher.publishEvent(new SignedUpEvent(member));

		log.info("SignedUpEvent published asynchronously for member ID: {}", member.getId());

	}

}
