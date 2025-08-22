(async function() {
	const res = await fetch('/admin/stats/counts', { cache: 'no-store' });
	if (!res.ok) { alert('통계 데이터를 불러오지 못했습니다.'); return; }
	const data = await res.json();


	const fmt = (n) => (n || 0).toLocaleString();

	const labels = ['회원 수', '자라는 나무', '다 키운 나무'];
	const values = [data.users || 0, data.growingTrees || 0, data.grownTrees || 0];
	const colors = ['#93c5fd', '#86efac', '#fcd34d'];

	const ctx = document.getElementById('barChart').getContext('2d');
	const chart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels,
			datasets: [{
				label: '현황',
				data: values,
				backgroundColor: colors,
				borderColor: '#e2e8f0',
				borderWidth: 1,
				borderRadius: 8,
				maxBarThickness: 64
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			scales: {
				x: { grid: { display: false } },
				y: {
					beginAtZero: true,
					ticks: { precision: 0 },
					grid: { color: 'rgba(148,163,184,.2)' }
				}
			},
			plugins: {
				legend: { display: false },
				tooltip: {
					callbacks: {
						label: (ctx) => {
							const v = ctx.parsed.y ?? ctx.parsed;
							return ` ${ctx.label}: ${v.toLocaleString()}`;
						}
					}
				}

			},
			animation: { duration: 600 }
		}
	});

	// 커스텀 범례
	const legend = document.getElementById('chartLegend');
	legend.innerHTML = '';
	labels.forEach((label, i) => {
		const item = document.createElement('div');
		item.className = 'item';
		const dot = document.createElement('span');
		dot.className = 'dot';
		dot.style.background = colors[i];
		const text = document.createElement('span');
		text.textContent = `${label} : ${fmt(values[i])}`;
		item.appendChild(dot); item.appendChild(text); legend.appendChild(item);
	});
})();
